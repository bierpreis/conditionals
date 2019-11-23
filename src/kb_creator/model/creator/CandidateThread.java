package kb_creator.model.creator;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.knowledge_base.ObjectKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealListPair;
import kb_creator.model.propositional_logic.PConditional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class CandidateThread implements Runnable {

    private BlockingQueue<AbstractKnowledgeBase> consistentKBsQueue;
    private BlockingQueue<AbstractKnowledgeBase> inconsistentKBsQueue;

    private BlockingQueue<AbstractPair> inputPairsQueue;
    private BlockingQueue<AbstractPair> outputPairsQueue;

    public CandidateThread(BlockingQueue<AbstractKnowledgeBase> consistentKBsQueue, BlockingQueue<AbstractKnowledgeBase> inconsistentKBsQueue, BlockingQueue<AbstractPair> inputPairsQueue, BlockingQueue<AbstractPair> outputPairsQueue) {

        //todo idea: collect not to this queue but to a collector which orders and then puts to this queue
        this.consistentKBsQueue = consistentKBsQueue;
        this.inconsistentKBsQueue = inconsistentKBsQueue;

        this.inputPairsQueue = inputPairsQueue;
        this.outputPairsQueue = outputPairsQueue;

    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {

            //line 8
            AbstractPair candidatePair;
            try {
                candidatePair = inputPairsQueue.take();
            } catch (InterruptedException e) {
                return; //triggers when thread is closed or finished
            }


            //line 9
            for (PConditional r : candidatePair.getCandidatesList()) {


                //line 10
                if (candidatePair.getKnowledgeBase().isConsistentWith(r)) {// takes almost no time


                    //line 11 and 12
                    AbstractKnowledgeBase knowledgeBaseToAdd = new ObjectKnowledgeBase(candidatePair.getKnowledgeBase(), r);//very little time
                    try {
                        consistentKBsQueue.put(knowledgeBaseToAdd);
                    } catch (InterruptedException e) {
                        return;
                    }
                    List<PConditional> candidatesToAdd = new ArrayList<>();
                    for (PConditional conditionalFromCandidates : candidatePair.getCandidatesList()) {//loop takes most of the time
                        if (conditionalFromCandidates.getNumber() > r.getNumber() && !conditionalFromCandidates.equals(r.getCounterConditional()))
                            candidatesToAdd.add(conditionalFromCandidates);
                    }
                    try {
                        outputPairsQueue.put(new RealListPair(knowledgeBaseToAdd, candidatesToAdd));
                    } catch (InterruptedException e) {
                        return; //triggers when thread is closed or finished
                    }
                } else try {
                    inconsistentKBsQueue.put(new ObjectKnowledgeBase(candidatePair.getKnowledgeBase(), r));
                } catch (
                        InterruptedException e) {
                    return; //triggers when thread is closed or finished
                }
            }
            candidatePair.clear();  //saves a lot of memory, takes almost no time
        }
        System.out.println("candidate thread finished");
    }

}
