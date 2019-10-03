package kb_creator.model.creator;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelCreator extends AbstractCreator {

    //todo: gui should set this
    private int numberOfThreads = 2;


    private BlockingQueue<AbstractKnowledgeBase> consistentQueue = new ArrayBlockingQueue<>(500);
    private BlockingQueue<AbstractKnowledgeBase> inConsistentQueue = new ArrayBlockingQueue<>(500);

    private BlockingQueue<AbstractPair> inputPairsQueue = new ArrayBlockingQueue<>(500);
    private BlockingQueue<AbstractPair> outputPairsQueue = new ArrayBlockingQueue<>(500);

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    private List<Future> futureList = new ArrayList<>(numberOfThreads);


    public ParallelCreator(AbstractSignature signature, String kbFilePath) {
        super(signature, kbFilePath);
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

            waitAndStopThreads();

            //this is line 8
            while (l.hasMoreElements(k)) {

                progress = calculateProgress(iterationPairCounter, lastIterationAmount);

                if (inputPairsQueue.remainingCapacity() > 0)
                    try {
                        inputPairsQueue.put(l.getNextPair(k));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                iterationPairCounter++;


                //todo: maybe put inconsistent and consistent counters in kb writer?!

                for (AbstractPair pair : outputPairsQueue) {
                    l.addPair(pair);
                    nextCandidatePairAmount++;

                }

                for (AbstractKnowledgeBase knowledgeBase : consistentQueue) {
                    kbWriter.addConsistentKb(knowledgeBase);
                    iterationNumberOfKBs++;
                    totalNumberOfKBs++;
                }

                //todo: what to do with inconsistent queue?

                //this both takes almost no time
                checkIfWaitForWriter(); //todo: this could be done with putting kbs into blocking queue


                if (creatorStatus.equals(CreatorStatus.STOPPED))
                    return;

                System.out.println("!!at end of loop");
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

            CandidateThread thread = new CandidateThread(i, consistentQueue, inConsistentQueue, inputPairsQueue, outputPairsQueue);

            futureList.add(executorService.submit(thread));
        }
    }

    private void waitAndStopThreads() {

        while (!inputPairsQueue.isEmpty())
            try {
                System.out.println("creator sleeping! " + inputPairsQueue.size()); //todo
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        for (Future future : futureList)
            future.cancel(true);
    }


    @Override
    public void stop() {
        super.stop();
        waitAndStopThreads();
    }

    @Override
    protected void addConsistentKb(AbstractKnowledgeBase knowledgeBase) {
        try {
            inConsistentQueue.put(knowledgeBase);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
