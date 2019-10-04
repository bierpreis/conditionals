package kb_creator.model.creator;
import kb_creator.model.propositional_logic.NewConditional;
import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.knowledge_base.ObjectKnowledgeBase;

import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.propositional_logic.signature.AbstractSignature;


import java.util.*;

public class SimpleCreator extends AbstractCreator {


    public SimpleCreator(AbstractSignature signature, String kbFilePath) {
        super(signature, kbFilePath);
        System.out.println("new simple creator");

    }

    //todo: recreate this
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

                AbstractPair candidatePair = l.getNextPair(k);

                iterationPairCounter++;
                //line 9
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
                        consistentWriterQueue.add(knowledgeBaseToAdd);
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
                        l.addPair(knowledgeBaseToAdd, candidatesToAdd);
                        //System.out.println("adding time: " + (System.nanoTime() - beforeAddingPair) / 1000);

                        nextCandidatePairAmount++;
                        iterationNumberOfKBs++;
                        totalNumberOfKBs++;

                        //save inconsistent knowledge base
                        //this part takes almost no time
                    } else addInconsistentKb(candidatePair.getKnowledgeBase(), r);
                    //System.out.println("complete time: " + (System.nanoTime() - overallStart) / 1000);
                }
                //this both takes almost no time
                if (creatorStatus.equals(CreatorStatus.STOPPED))
                    return;
                //this saves a lot of memory
                //this takes almost no time
                candidatePair.clear();

            }
            l.finishIteration(k);
            k = k + 1;
        }
        l.setFinished();
        creatorStatus = CreatorStatus.FINISHED;
    }

    private void addInconsistentKb(AbstractKnowledgeBase knowledgeBase, NewConditional conditionalToAdd) {
        try {
            inconsistentWriterQueue.put(new ObjectKnowledgeBase(iterationNumberOfKBs, knowledgeBase, conditionalToAdd)); //todo: why number for inconsistent kb???
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        totalInconsistentAmount++; //todo: counter in writer?
    }


    @Override
    public void stop() {
        super.stop();

    }

}




