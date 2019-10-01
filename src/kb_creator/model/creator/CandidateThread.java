package kb_creator.model.creator;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.knowledge_base.ObjectKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.propositional_logic.NewConditional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class CandidateThread implements Runnable {

    private int threadNumber;

    private BlockingQueue<AbstractKnowledgeBase> consistentQueue;
    private BlockingQueue<AbstractKnowledgeBase> inconsistentQueue;

    private BlockingQueue<AbstractPair> inputQueue;
    private BlockingQueue<AbstractPair> outputqueue;
    private volatile boolean running = true;

    //todo: add queues
    public CandidateThread(int threadNumber, BlockingQueue<AbstractKnowledgeBase> consistentQueue, BlockingQueue<AbstractKnowledgeBase> inconsistentQueue, BlockingQueue<AbstractPair> inputQueue, BlockingQueue<AbstractPair> outputQueue) {
        this.threadNumber = threadNumber;

        this.consistentQueue = consistentQueue;
        this.inconsistentQueue = inconsistentQueue;

        this.inputQueue = inputQueue;
        this.outputqueue = outputQueue;


    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {

            System.out.println("thread " + threadNumber + " running...");

            AbstractPair candidatePair = null;

            try {
                candidatePair = inputQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (NewConditional r : candidatePair.getCandidatesList()) {
                long overallStart = System.nanoTime();
                //line 10 //
                //consistency check takes almost no time
                if (candidatePair.getKnowledgeBase().isConsistent(r)) {
                    //System.out.println("consistency check: " + (System.nanoTime() - overallStart) / 1000);
                    //next part is line 11 and 12

                    //long kbCreationStart = System.nanoTime();
                    //first create the new knowledge base
                    //takes very little time
                    AbstractKnowledgeBase knowledgeBaseToAdd = new ObjectKnowledgeBase(iterationNumberOfKBs, candidatePair.getKnowledgeBase(), r);
                    kbWriter.addConsistentKb(knowledgeBaseToAdd);
                    //System.out.println("kb creation:: " + (System.nanoTime() - kbCreationStart) / 1000);

                    //long beforeCandidates = System.nanoTime();
                    //create candidates set
                    //this loop takes most of the time (70 percent)
                    List<NewConditional> candidatesToAdd = new ArrayList<>();
                    for (NewConditional conditionalFromCandidates : candidatePair.getCandidatesList())
                        if (conditionalFromCandidates.getNumber() > r.getNumber() && !conditionalFromCandidates.equals(r.getCounterConditional()))
                            candidatesToAdd.add(conditionalFromCandidates);
                    //System.out.println("candidate time: " + (System.nanoTime() - beforeCandidates) / 1000);

                    //line 12
                    //this takes about 30 percent of time
                    //collecting pairs and add together is even slower
                    long beforeAddingPair = System.nanoTime();

                    //todo: add to new pair queue
                    l.addPair(knowledgeBaseToAdd, candidatesToAdd);
                    //System.out.println("adding time: " + (System.nanoTime() - beforeAddingPair) / 1000);

                    nextCandidatePairAmount++;
                    iterationNumberOfKBs++;
                    totalNumberOfKBs++;

                    //save inconsistent knowledge base
                    //this part takes almost no time
                } else inconsistentQueue.add(new ObjectKnowledgeBase(candidatePair.getKnowledgeBase(), r));
                //System.out.println("complete time: " + (System.nanoTime() - overallStart) / 1000);

                //todo: is this correct position?
                //this saves a lot of memory
                //this takes almost no time
                candidatePair.clear();
            }
        }
    }

    public void askToStop() {
        running = false;
    }
}
