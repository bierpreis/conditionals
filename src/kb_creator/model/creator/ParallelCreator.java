package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelCreator extends AbstractCreator {

    //todo: gui should set this
    private int numberOfThreads = 3;

    private ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

    private List<Future> futureList = new ArrayList<>(numberOfThreads);

    private QueueTakerThread queueTakerThread;
    private QueuePutterThread queuePutterThread;

    private int allIterationsBeforeCounter = 0;


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


        //line 6
        while (l.hasElementsForNextK(k)) {
            System.out.println("beginning iteration: " + k);
            System.gc();

            //todo: the next 2 lines were twisted. k like that?
            l.prepareIteration(k);

            startThreads(k);


            allIterationsBeforeCounter = iterationNumberOfKBs;
            int currentiterationPairCounter = 0;
            lastIterationAmount = nextCandidatePairAmount;
            nextCandidatePairAmount = 0;
            iterationNumberOfKBs = 0;

            //line  7
            l.addNewList(new ArrayList<>());

            //this is line 8
            while (l.hasMoreElements(k)) {

                progress = calculateProgress(currentiterationPairCounter, lastIterationAmount);

                currentiterationPairCounter = queuePutterThread.getCounter();

                nextCandidatePairAmount = queueTakerThread.getCounter();

                //todo: make this work
                totalNumberOfKBs = allIterationsBeforeCounter + currentiterationPairCounter;

                System.out.println("in queue: " + inputPairsQueue.size()); //todo: input pairs full!
                System.out.println("out queue: " + outputPairsQueue.size());

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
        //todo: check if threads are closed correctly at this point.
        l.setFinished();
        creatorStatus = CreatorStatus.FINISHED;
    }

    private void startThreads(int currentK) {

        queueTakerThread = new QueueTakerThread(outputPairsQueue, l, currentK);
        new Thread(queueTakerThread).start();

        queuePutterThread = new QueuePutterThread(inputPairsQueue, l, currentK);
        new Thread(queuePutterThread).start();


        for (int i = 0; i < numberOfThreads; i++) {

            CandidateThread thread = new CandidateThread(i, consistentWriterQueue, inconsistentWriterQueue, inputPairsQueue, outputPairsQueue);

            futureList.add(executorService.submit(thread));
        }


    }

    //todo: stop queue taker when finished but how?
    private void waitAndStopThreads() {
        System.out.println("wait and stop threads");
        while (!inputPairsQueue.isEmpty()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Future future : futureList)
            future.cancel(true);

        System.out.println("creator threads canceled");
        queueTakerThread.stopWhenFinished();
    }


    @Override
    public void stop() {
        super.stop();
        waitAndStopThreads();
        queueTakerThread.stopWhenFinished();
    }


}
