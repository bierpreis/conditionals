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

    private BlockingQueue<AbstractKnowledgeBase> consistentQueue;
    private BlockingQueue<AbstractKnowledgeBase> inconsistentQueue;

    private BlockingQueue<AbstractPair> inputQueue;
    private BlockingQueue<AbstractPair> outputQueue;
    private volatile boolean running = true;

    public CandidateThread(BlockingQueue<AbstractKnowledgeBase> consistentQueue, BlockingQueue<AbstractKnowledgeBase> inconsistentQueue, BlockingQueue<AbstractPair> inputQueue, BlockingQueue<AbstractPair> outputQueue) {
        this.consistentQueue = consistentQueue;
        this.inconsistentQueue = inconsistentQueue;

        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;

    }

    @Override
    public void run() {
        while (running) {
            AbstractPair candidatePair = null;

            try {
                candidatePair = inputQueue.take();
            } catch (InterruptedException e) {
                //intentionally nothing
                //this interrupt happens when thread is waiting for queue but gets closed because iteration is finished
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
                    AbstractKnowledgeBase knowledgeBaseToAdd = new ObjectKnowledgeBase(candidatePair.getKnowledgeBase(), r);

                    try {
                        consistentQueue.put(knowledgeBaseToAdd);
                    } catch (InterruptedException e) {
                        //intentionally nothing
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

                    try {
                        outputQueue.put(new RealListPair(knowledgeBaseToAdd, candidatesToAdd));
                    } catch (InterruptedException e) {
                        //intentionally nothing
                    }

                    //save inconsistent knowledge base
                    //this part takes almost no time
                } else try {
                    inconsistentQueue.put(new ObjectKnowledgeBase(candidatePair.getKnowledgeBase(), r));
                } catch (
                        InterruptedException e) {
                    //intentionally nothing
                }
            }
            //this saves a lot of memory
            //this takes almost no time
            candidatePair.clear();

        }
        if (!inputQueue.isEmpty())
            throw new RuntimeException("Finished when input not empty! Elements left: " + inputQueue.size());
    }

    public void stop() {
        running = false;
    } //todo: why is this never used? remove running variable completely?
}
