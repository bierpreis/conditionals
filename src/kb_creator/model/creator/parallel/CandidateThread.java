package kb_creator.model.creator.parallel;


import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealListPair;
import kb_creator.model.propositional_logic.KnowledgeBase;
import kb_creator.model.propositional_logic.PConditional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class CandidateThread implements Runnable {

    private BlockingQueue<KnowledgeBase> consistentKBsQueue;
    private BlockingQueue<KnowledgeBase> inconsistentKBsQueue;

    private BlockingQueue<AbstractPair> inputPairsQueue;
    private BlockingQueue<AbstractPair> outputPairsQueue;

    public CandidateThread(BlockingQueue<KnowledgeBase> consistentKBsQueue, BlockingQueue<KnowledgeBase> inconsistentKBsQueue, BlockingQueue<AbstractPair> inputPairsQueue, BlockingQueue<AbstractPair> outputPairsQueue) {

        //todo idea: collect not to this queue but to a collector which orders and then puts to this queue
        //needed: kb collector (inconsistent and consistent) and pair collector
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
                //todo: at this point the number of pair.kb could be used to create a return pair!?
            } catch (InterruptedException e) {
                return; //triggers when thread is closed or finished
            }


            //line 9
            for (PConditional r : candidatePair.getCandidatesList()) {


                //line 10
                if (candidatePair.getKnowledgeBase().isConsistentWith(r)) {// takes almost no time


                    //line 11 and 12
                    KnowledgeBase knowledgeBaseToAdd = new KnowledgeBase(candidatePair.getKnowledgeBase(), r);//very little time
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
                    inconsistentKBsQueue.put(new KnowledgeBase(candidatePair.getKnowledgeBase(), r));
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
