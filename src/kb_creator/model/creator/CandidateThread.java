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

    public CandidateThread(BlockingQueue<AbstractKnowledgeBase> consistentQueue, BlockingQueue<AbstractKnowledgeBase> inconsistentQueue, BlockingQueue<AbstractPair> inputQueue, BlockingQueue<AbstractPair> outputQueue) {
        this.consistentQueue = consistentQueue;
        this.inconsistentQueue = inconsistentQueue;

        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;

    }

    //todo: comments and line breaks and line numbers
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            AbstractPair candidatePair;

            //line 8
            try {
                candidatePair = inputQueue.take();
            } catch (InterruptedException e) { //this interrupt happens when thread is waiting for queue but gets closed because iteration is finished
                return;
            }


            //line 9
            for (NewConditional r : candidatePair.getCandidatesList()) {


                //line 10
                if (candidatePair.getKnowledgeBase().isConsistent(r)) {// takes almost no time


                    //next part is line 11 and 12

                    AbstractKnowledgeBase knowledgeBaseToAdd = new ObjectKnowledgeBase(candidatePair.getKnowledgeBase(), r);//very little time
                    try {
                        consistentQueue.put(knowledgeBaseToAdd);
                    } catch (InterruptedException e) {
                        return;
                    }

                    List<NewConditional> candidatesToAdd = new ArrayList<>();
                    for (NewConditional conditionalFromCandidates : candidatePair.getCandidatesList()) {//this loop takes most of the time
                        if (conditionalFromCandidates.getNumber() > r.getNumber() && !conditionalFromCandidates.equals(r.getCounterConditional()))
                            candidatesToAdd.add(conditionalFromCandidates);
                    }
                    try {
                        outputQueue.put(new RealListPair(knowledgeBaseToAdd, candidatesToAdd));
                    } catch (InterruptedException e) {
                        return; //triggers when thread is closed
                    }
                } else try {
                    inconsistentQueue.put(new ObjectKnowledgeBase(candidatePair.getKnowledgeBase(), r));
                } catch (
                        InterruptedException e) {
                    return; //triggers when thread is closed
                }
            }
            candidatePair.clear();  //saves a lot of memory, takes almost no time
        }
        System.out.println("candidate thread finished");
    }

}
