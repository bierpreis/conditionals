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
        l.finishIteration(0); //actually this is iteration 0


        //line 6
        while (l.hasElementsForIteration(k)) {
            long startTime = System.currentTimeMillis();
            System.gc();
            l.prepareIteration(k);
            currentPairAmount = kbWriter.getIterationConsistentCounter();
            kbWriter.newIteration(k);
            iterationPairCounter = 0;


            //line  7
            l.addNewList(new ArrayList<>());


            //line 8
            while (l.hasMoreElementsForK(k)) {
                AbstractPair currentPair = l.getNextPair(k);
                iterationPairCounter++;


                //line 9
                for (NewConditional r : currentPair.getCandidatesList()) {


                    //line 10
                    if (currentPair.getKnowledgeBase().isConsistentWith(r)) { //takes almost no time


                        //next part is line 11 and 12
                        AbstractKnowledgeBase knowledgeBaseToAdd = new ObjectKnowledgeBase(currentPair.getKnowledgeBase(), r); //takes little time
                        try {
                            consistentWriterQueue.put(knowledgeBaseToAdd);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        List<NewConditional> candidatesToAdd = new ArrayList<>();
                        for (NewConditional conditionalFromCandidates : currentPair.getCandidatesList()) //loop takes most of the time (70 percent)
                            if (conditionalFromCandidates.getNumber() > r.getNumber() && !conditionalFromCandidates.equals(r.getCounterConditional())) //equals is faster then comparing numbers here.
                                candidatesToAdd.add(conditionalFromCandidates);

                        //line 12
                        l.addPair(knowledgeBaseToAdd, candidatesToAdd); //this takes about 30 percent of time


                    } else addInconsistentKb(currentPair.getKnowledgeBase(), r); //takes almost no time
                }
                if (creatorStatus.equals(CreatorStatus.STOPPED))
                    return;
                currentPair.clear(); //saves a lot of memory and takes almost no time

            }
            System.out.println("time for iteration " + k + ": " + (System.currentTimeMillis() - startTime) / 1000 + "s");
            //line 13
            kbWriter.finishIteration();
            l.finishIteration(k);
            k = k + 1;
        }
        creatorStatus = CreatorStatus.FINISHED;
        super.finishAndStopLoop();
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




