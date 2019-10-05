package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.AbstractPair;
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


    public ParallelCreator(AbstractSignature signature, String kbFilePath, AbstractPairBuffer l) {
        super(signature, kbFilePath, l);
        System.out.println("new parallel creator");

        queueTakerThread = new QueueTakerThread(outputPairsQueue, l);
        new Thread(queueTakerThread).start();

        queuePutterThread = new QueuePutterThread(inputPairsQueue, l);
        new Thread(queuePutterThread).start();


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
        while (l.hasElementsForK(k)) {
            System.gc();

            startThreads();

            l.prepareIteration(k);


            int iterationPairCounter = 0;
            lastIterationAmount = nextCandidatePairAmount;
            nextCandidatePairAmount = 0;
            iterationNumberOfKBs = 0;

            //line  7
            l.addNewList(new ArrayList<>());

            //this is line 8
            while (l.hasMoreElements(k)) {

                progress = calculateProgress(iterationPairCounter, lastIterationAmount);


                //todo: make this static in creator thread? what about other counters?
                iterationPairCounter++;


                //todo: maybe put inconsistent and consistent counters in kb writer?!


                //todo: counters need to be in writer not here

                iterationNumberOfKBs++;
                totalNumberOfKBs++;


                //todo: what to do with inconsistent queue?

                if (creatorStatus.equals(CreatorStatus.STOPPED))
                    return;
            }
            waitAndStopThreads();

            l.finishIteration(k);
            k = k + 1;
        }
        //todo: check if threads are closed correctly at this point.
        l.setFinished();
        creatorStatus = CreatorStatus.FINISHED;
    }

    private void startThreads() {

        for (int i = 0; i < numberOfThreads; i++) {

            CandidateThread thread = new CandidateThread(i, consistentWriterQueue, inconsistentWriterQueue, inputPairsQueue, outputPairsQueue);

            futureList.add(executorService.submit(thread));
        }
    }

    //todo: reactivate
    private void waitAndStopThreads() {
        while (!inputPairsQueue.isEmpty()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Future future : futureList)
            future.cancel(true);
    }


    @Override
    public void stop() {
        super.stop();
        waitAndStopThreads();
        queuePutterThread.stop();
        queueTakerThread.stop();
    }


}
