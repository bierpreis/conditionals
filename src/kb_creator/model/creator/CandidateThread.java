package kb_creator.model.creator;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.knowledge_base.ObjectKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealListPair;
import kb_creator.model.propositional_logic.NewConditional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class CandidateThread implements Runnable {

    private int threadNumber;

    private BlockingQueue<AbstractKnowledgeBase> consistentQueue;
    private BlockingQueue<AbstractKnowledgeBase> inconsistentQueue;

    private BlockingQueue<AbstractPair> inputQueue;
    private BlockingQueue<AbstractPair> outputQueue;
    private volatile boolean running = true;

    public CandidateThread(int threadNumber, BlockingQueue<AbstractKnowledgeBase> consistentQueue, BlockingQueue<AbstractKnowledgeBase> inconsistentQueue, BlockingQueue<AbstractPair> inputQueue, BlockingQueue<AbstractPair> outputQueue) {
        this.threadNumber = threadNumber;

        this.consistentQueue = consistentQueue;
        this.inconsistentQueue = inconsistentQueue;

        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        while (running) {

            AbstractPair candidatePair = null;

            System.out.println("Thread " + threadNumber + " before take");
            try {
                candidatePair = inputQueue.take();
            } catch (InterruptedException e) {
                System.out.println("thread " + threadNumber + " was interrupted.");
            }
            System.out.println("Thread " + threadNumber + " after take");

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
                    AbstractKnowledgeBase knowledgeBaseToAdd = new ObjectKnowledgeBase(candidatePair.getKnowledgeBase(), r);

                    try {
                        System.out.println("consistent size: " + consistentQueue.size());
                        consistentQueue.put(knowledgeBaseToAdd);
                        System.out.println("after put");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("kb creation:: " + (System.nanoTime() - kbCreationStart) / 1000);

                    //long beforeCandidates = System.nanoTime();
                    //create candidates set
                    //this loop takes most of the time (70 percent)
                    List<NewConditional> candidatesToAdd = new ArrayList<>();
                    for (NewConditional conditionalFromCandidates : candidatePair.getCandidatesList())
                        if (conditionalFromCandidates.getNumber() > r.getNumber() && !conditionalFromCandidates.equals(r.getCounterConditional()))
                            candidatesToAdd.add(conditionalFromCandidates);
                    //System.out.println("candidate time: " + (System.nanoTime() - beforeCandidates) / 1000);

                    outputQueue.add(new RealListPair(knowledgeBaseToAdd, candidatesToAdd));

                    //save inconsistent knowledge base
                    //this part takes almost no time
                } else
                    inconsistentQueue.add(new ObjectKnowledgeBase(candidatePair.getKnowledgeBase(), r));


                //System.out.println("complete time: " + (System.nanoTime() - overallStart) / 1000);


            }
            //this saves a lot of memory
            //this takes almost no time
            candidatePair.clear();

            System.out.println("thread " + this.threadNumber + " finished one pair");
        }
        System.out.println("!!! thread finished");
    }

    public void stop(){
        running = false;
    }
}
