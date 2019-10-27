package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.propositional_logic.NewConditional;
import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.knowledge_base.ObjectKnowledgeBase;

import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.propositional_logic.signature.AbstractSignature;


import java.util.*;

public class SimpleCreator extends AbstractCreator {

    private int iterationPairCounter = 0;

    public SimpleCreator(AbstractSignature signature, String kbFilePath, AbstractPairBuffer l) {
        super(signature, kbFilePath, l);
        System.out.println("new simple creator");

    }


    @Override
    public void run() {
        creatorStatus = CreatorStatus.RUNNING;
        System.out.println("creator thread started");
        l.prepareIteration(0);

        //line 2
        k = 1;


        //line 3-5
        l.addNewList(initOneElementKBs(nfc, cnfc));
        //0 because actually the init list is iteration 0
        l.finishIteration(0);


        //line 6
        while (l.hasElementsForNextK(k)) {
            System.gc();
            l.prepareIteration(k);
            currentPairAmount = kbWriter.getIterationConsistentCounter();
            kbWriter.newIteration();
            iterationPairCounter = 0;


            //line  7
            l.addNewList(new ArrayList<>());


            //line 8
            while (l.hasMoreElementsForK(k)) {
                AbstractPair candidatePair = l.getNextPair(k);
                iterationPairCounter++;


                //line 9
                for (NewConditional r : candidatePair.getCandidatesList()) {


                    //line 10
                    //consistency check takes almost no time
                    if (candidatePair.getKnowledgeBase().isConsistent(r)) {


                        //next part is line 11 and 12
                        //takes very little time
                        AbstractKnowledgeBase knowledgeBaseToAdd = new ObjectKnowledgeBase(candidatePair.getKnowledgeBase(), r);
                        try {
                            consistentWriterQueue.put(knowledgeBaseToAdd);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //create candidates set
                        //this loop takes most of the time (70 percent)
                        List<NewConditional> candidatesToAdd = new ArrayList<>();
                        for (NewConditional conditionalFromCandidates : candidatePair.getCandidatesList())
                            if (conditionalFromCandidates.getNumber() > r.getNumber() && !conditionalFromCandidates.equals(r.getCounterConditional()))
                                candidatesToAdd.add(conditionalFromCandidates);

                        //line 12
                        //this takes about 30 percent of time, collecting pairs and add together is even slower
                        l.addPair(knowledgeBaseToAdd, candidatesToAdd);

                        //save inconsistent knowledge base, takes almost no time
                    } else addInconsistentKb(candidatePair.getKnowledgeBase(), r);
                }
                if (creatorStatus.equals(CreatorStatus.STOPPED))
                    return;
                //this saves a lot of memory and takes almost no time
                candidatePair.clear();

            }
            //line 13
            kbWriter.finishIteration();
            l.finishIteration(k);
            k = k + 1;
        }
        l.setFinished();
        creatorStatus = CreatorStatus.FINISHED;
        super.finish();
    }

    private void addInconsistentKb(AbstractKnowledgeBase knowledgeBase, NewConditional conditionalToAdd) {
        try {
            inconsistentWriterQueue.put(new ObjectKnowledgeBase(knowledgeBase, conditionalToAdd));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getTotalInconsistentAmount() {
        return kbWriter.getTotalInconsistentCounter();
    }


    @Override
    public float calculateProgress() {

        //avoid division with zero
        if (currentPairAmount == 0) {
            return 0;

        }
        return (iterationPairCounter / (float) currentPairAmount) * 100;
    }


    @Override
    public void stopLoop() {
        super.stopLoop();

    }

}




