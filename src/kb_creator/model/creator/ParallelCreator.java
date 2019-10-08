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

    private OutputQueueThread outputQueueThread;
    private InputQueueThread inputQueueThread;

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

            //line  7
            l.addNewList(new ArrayList<>());

            l.prepareIteration(k);

            startThreads(k);


            allIterationsBeforeCounter = iterationNumberOfKBs;
            int currentiterationPairCounter = 0;
            lastIterationAmount = nextCandidatePairAmount;
            nextCandidatePairAmount = 0;
            iterationNumberOfKBs = 0;



            //this is line 8
            while (l.hasMoreElements(k)) {

                progress = calculateProgress(currentiterationPairCounter, lastIterationAmount);

                currentiterationPairCounter = inputQueueThread.getCounter();

                nextCandidatePairAmount = outputQueueThread.getCounter();

                //todo: make this work with abc
                totalNumberOfKBs = allIterationsBeforeCounter + currentiterationPairCounter;

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
    }

    private void startThreads(int currentK) {

        outputQueueThread = new OutputQueueThread(outputPairsQueue, l, currentK);
        new Thread(outputQueueThread).start();

        inputQueueThread = new InputQueueThread(inputPairsQueue, l, currentK);
        new Thread(inputQueueThread).start();


        for (int i = 0; i < numberOfThreads; i++) {

            CandidateThread thread = new CandidateThread(i, consistentWriterQueue, inconsistentWriterQueue, inputPairsQueue, outputPairsQueue);

            futureList.add(executorService.submit(thread));
        }


    }

    private void waitAndStopThreads() {
        System.out.println("wait and stop threads");
        while (!inputPairsQueue.isEmpty()) {
            try {
                System.out.println("creator waiting to finish. queue: " + inputPairsQueue.size()); //todo: this sleeps always when stopped by button
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Future future : futureList)
            future.cancel(true);

        System.out.println("creator threads canceled");
        outputQueueThread.stopWhenFinished();
    }


    @Override
    public void stop() {
        super.stop();
        waitAndStopThreads();
        outputQueueThread.stopWhenFinished();
    }


}
