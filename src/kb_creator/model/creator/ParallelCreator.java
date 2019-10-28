package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelCreator extends AbstractCreator {

    private int numberOfThreads;

    private ExecutorService executorService;

    private List<Future> futureList = new ArrayList<>(numberOfThreads);

    private OutputQueueThread outputQueueObject;
    private InputQueueThread inputQueueObject;

    private Thread outputQueueThread;
    private Thread inputQueueThread;


    public ParallelCreator(AbstractSignature signature, String kbFilePath, int numberOfThreads, AbstractPairBuffer l) {
        super(signature, kbFilePath, l);
        executorService = Executors.newFixedThreadPool(numberOfThreads);
        System.out.println("new parallel creator");
        this.numberOfThreads = numberOfThreads;
    }


    @Override
    public void run() {
        creatorStatus = CreatorStatus.RUNNING;
        System.out.println("creator thread started");
        l.prepareIteration(0);
        kbWriter.newIteration();


        //line 2
        k = 1;


        //line 3-5
        l.addNewList(initOneElementKBs(nfc, cnfc));
        l.finishIteration(0); //actually this is iteration 0


        //line 6
        while (l.hasElementsForNextK(k)) {
            System.out.println("beginning iteration: " + k);
            System.gc();


            //line  7
            l.addNewList(new ArrayList<>());

            l.prepareIteration(k);
            currentPairAmount = kbWriter.getIterationConsistentCounter();
            kbWriter.newIteration();
            startIterationThreads(k);


            while (l.hasMoreElementsForK(k)) {

                if (creatorStatus.equals(CreatorStatus.STOPPED))
                    return;

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            waitAndStopThreads();

            kbWriter.finishIteration();
            l.finishIteration(k);


            //line 13
            k = k + 1;
        }

        l.setFinished();
        executorService.shutdown();
        creatorStatus = CreatorStatus.FINISHED;
        super.finishAndStopLoop();
    }

    private void startIterationThreads(int currentK) {
        if (!inputPairsQueue.isEmpty() || !outputPairsQueue.isEmpty())
            throw new RuntimeException("Queue was not empty! Sth will get lost!");

        inputQueueObject = new InputQueueThread(inputPairsQueue, l, currentK);
        inputQueueThread = new Thread(inputQueueObject);
        inputQueueThread.setName("InputQueueThread");
        inputQueueThread.start();

        outputQueueObject = new OutputQueueThread(outputPairsQueue, l);
        outputQueueThread = new Thread(outputQueueObject);
        outputQueueThread.setName("OutputQueueThread");
        outputQueueThread.start();


        for (int i = 0; i < numberOfThreads; i++) {

            CandidateThread thread = new CandidateThread(consistentWriterQueue, inconsistentWriterQueue, inputPairsQueue, outputPairsQueue);

            //this is to avoid adding tasks to already shutdown executor service. this only happens when stop button is pressed and things are shutting down.
            if (!executorService.isShutdown())
                futureList.add(executorService.submit(thread));


        }


    }

    private void waitAndStopThreads() {
        while (!inputPairsQueue.isEmpty()) {
            try {
                System.out.println("creator waiting to finish. queue: " + inputPairsQueue.size());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Future future : futureList)
            future.cancel(true);

        System.out.println("creator threads canceled");
        outputQueueObject.finishQueueAndStop();
        outputQueueThread.interrupt();

    }


    @Override
    public void stopLoop() {
        super.stopLoop();

        for (Future future : futureList)
            future.cancel(true);

        executorService.shutdownNow();

        outputQueueObject.finishQueueAndStop();
        outputQueueThread.interrupt();

        inputQueueThread.interrupt();
    }

    @Override
    public float calculateProgress() {
        //avoid division with zero
        if (currentPairAmount == 0) {
            return 0;

        }
        return (inputQueueObject.getCounter() / (float) currentPairAmount) * 100; //todo. null pointer with buffering. must have been inputQueueObject!
    }

    @Override
    public int getTotalInconsistentAmount() {
        return kbWriter.getTotalInconsistentCounter();
    }


}
