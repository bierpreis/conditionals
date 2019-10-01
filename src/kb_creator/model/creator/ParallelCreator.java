package kb_creator.model.creator;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.knowledge_base.ObjectKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.propositional_logic.NewConditional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class ParallelCreator extends AbstractCreator {

    //todo: gui should set this
    private int numberOfThreads = 2;


    private BlockingQueue<AbstractPair> inputQueue = new ArrayBlockingQueue<>(500);

    private BlockingQueue<AbstractKnowledgeBase> consistentQueue = new ArrayBlockingQueue<>(500);
    private BlockingQueue<AbstractKnowledgeBase> inConsistentQueue = new ArrayBlockingQueue<>(500);

    private BlockingQueue<AbstractPair> newPairsQueue = new ArrayBlockingQueue<>(500);

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    private List<CandidateThread> threadList = new ArrayList<>(numberOfThreads);

    //todo
    public ParallelCreator() {
        for (int i = 0; i < numberOfThreads; i++) {

            CandidateThread thread = new CandidateThread(i, consistentQueue, inConsistentQueue, newPairsQueue);
            threadList.add(thread);
            executorService.submit(thread);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("now interrupting!");

        for (CandidateThread thread : threadList)
            thread.askToStop();
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
                
                if (inputQueue.remainingCapacity() > 0)
                    try {
                        inputQueue.put(l.getNextPair(k));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                iterationPairCounter++;

                //todo: deal with inconsistent and consistent queues and counters and set numbers to consistent queues
                //maybe put inconsistent and consistent counters in kb writer?!

/*                nextCandidatePairAmount++;
                iterationNumberOfKBs++;
                totalNumberOfKBs++;*/


                //this both takes almost no time
                checkIfWaitForWriter();
                if (creatorStatus.equals(CreatorStatus.STOPPED))
                    return;


            }
            //todo: here wait until all pairs are processed and returned. but how? maybe wait untill threads will close

            l.finishIteration(k);
            k = k + 1;
        }
        //todo: close threads
        l.setFinished();
        creatorStatus = CreatorStatus.FINISHED;
    }


    @Override
    public void stop() {
        super.stop();
        //todo: interrupt single executors?

        executorService.shutdown();
    }

    @Override
    protected void addConsistentKb(AbstractKnowledgeBase knowledgeBase) {
        inConsistentQueue.add(knowledgeBase);
    }

    public static void main(String[] args) {
        new ParallelCreator();
    }


}
