package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelCreator extends AbstractCreator {

    //todo: gui should set this
    private int numberOfThreads = 4;

    private ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

    private List<Future> futureList = new ArrayList<>(numberOfThreads);

    private OutputQueueThread outputQueueObject;
    private InputQueueThread inputQueueThread;

    private Thread outputQueueThread;


    public ParallelCreator(AbstractSignature signature, String kbFilePath, AbstractPairBuffer l) {
        super(signature, kbFilePath, l);
        System.out.println("new parallel creator");


    }

    @Override
    public void run() {
        creatorStatus = CreatorStatus.RUNNING;
        System.out.println("creator thread started");
        l.prepareIteration(0);

        //line 2
        k = 1;

        //this is actually iteration 0
        //and line 3-5
        l.addNewList(initOneElementKBs(nfc, cnfc));

        //k - 1 because actually the init list is iteration 0
        l.finishIteration(0);

        //todo: iteration counter doesnt show in gui
        //line 6
        while (l.hasElementsForNextK(k)) {
            System.out.println("beginning iteration: " + k);
            System.gc();

            //line  7
            l.addNewList(new ArrayList<>());

            l.prepareIteration(k);

            startThreads(k);

            lastIterationAmount = nextCandidatePairAmount;
            nextCandidatePairAmount = 0;
            iterationNumberOfKBs = 0;


            //this is line 8
            while (l.hasMoreElementsForK(k)) {
                progress = calculateProgress(inputQueueThread.getCounter(), lastIterationAmount); //todo: currentIerationPairCounter is instantly queue max size. thats why progress starts at 30%

                nextCandidatePairAmount = outputQueueObject.getCounter();

                if (creatorStatus.equals(CreatorStatus.STOPPED))
                    return;

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            waitAndStopThreads();

            l.finishIteration(k);
            k = k + 1;
        }
        l.setFinished();
        creatorStatus = CreatorStatus.FINISHED;
        super.finish();
    }

    private void startThreads(int currentK) {
        if (!inputPairsQueue.isEmpty() || !outputPairsQueue.isEmpty())
            throw new RuntimeException("Queue was not empty! Sth will get lost!");

        outputQueueObject = new OutputQueueThread(outputPairsQueue, l);
        new Thread(outputQueueObject).start();

        inputQueueThread = new InputQueueThread(inputPairsQueue, l, currentK);

        outputQueueThread = new Thread(inputQueueThread); //todo: this is shit wtf !!
        outputQueueThread.start();


        for (int i = 0; i < numberOfThreads; i++) {

            CandidateThread thread = new CandidateThread(consistentWriterQueue, inconsistentWriterQueue, inputPairsQueue, outputPairsQueue);

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
        outputQueueObject.stopLoop();

    }

    private void instantStop() {
        for (Future future : futureList)
            future.cancel(true);
        //todo: stop input queue thread too
        outputQueueObject.stopLoop();
        outputQueueThread.interrupt();
    }


    @Override
    public void stop() {
        super.stop();
        instantStop();
        outputQueueObject.stopLoop();
        outputQueueThread.interrupt();
    }


}
