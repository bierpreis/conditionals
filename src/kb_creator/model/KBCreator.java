package kb_creator.model;

import kb_creator.model.pairs.RealListPair;
import kb_creator.model.propositional_logic.AbstractFormula;
import kb_creator.model.propositional_logic.NewConditional;
import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.knowledge_base.ObjectKnowledgeBase;
import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.propositional_logic.signature.AbstractSignature;
import kb_creator.model.writer.AbstractKbWriter;
import kb_creator.model.writer.KbDummyWriter;
import kb_creator.model.writer.KbFileWriter;
import nfc.model.NfcCreator;

import java.util.*;

public class KBCreator implements Runnable {

    private int totalNumberOfKBs;
    private int totalInconsistentAmount;
    private int iterationNumberOfKBs;
    private int lastIterationAmount;

    private CreatorStatus creatorStatus;
    private volatile boolean waitForKbWriter;

    private AbstractSignature signature;

    private int k;
    private int nextCandidatePairAmount;

    private long startTime;

    private AbstractKbWriter kbWriter;

    private AbstractPairBuffer l;

    private float progress;

    private Collection<NewConditional> nfc;

    private Collection<NewConditional> cnfc;


    public KBCreator(AbstractSignature signature, String kbFilePath) {
        System.out.println("new kb creator");

        AbstractFormula.setSignature(signature);
        AbstractKnowledgeBase.setSignature(signature);

        creatorStatus = CreatorStatus.NOT_STARTED;
        this.signature = signature;
        waitForKbWriter = false;

        //kbFilePath is null when no buffering is requested
        if (kbFilePath != null)
            kbWriter = new KbFileWriter(kbFilePath);
        else
            kbWriter = new KbDummyWriter();

        lastIterationAmount = 0;

        creatorStatus = CreatorStatus.CREATING_CONDITIONALS;

        NfcCreator nfcCreator = new NfcCreator(signature);

        nfc = Collections.unmodifiableCollection(nfcCreator.getNewNfc());

        cnfc = Collections.unmodifiableCollection(nfcCreator.getNewCnfc());

        AbstractPair.setNfc(nfcCreator.getNfcMap());
        AbstractKnowledgeBase.setNfcMap(nfcCreator.getNfcMap());

        Thread kbWriterThread = new Thread(kbWriter);
        kbWriterThread.start();

        startTime = System.currentTimeMillis();

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

                //todo: make sure if ordering is necessary. if not, threading could be useful. if yes, make sure it is ordered!
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
                checkIfWaitForWriter();
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
        AbstractKnowledgeBase inconsistentKB = new ObjectKnowledgeBase(iterationNumberOfKBs, knowledgeBase, conditionalToAdd);
        kbWriter.addInconsistentKb(inconsistentKB);
        totalInconsistentAmount++;
    }

    private void checkIfWaitForWriter() {
        if (waitForKbWriter)
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                waitForKbWriter = false;
            }

    }

    public void setSignature(AbstractSignature signature) {
        this.signature = signature;
    }


    public void setList(AbstractPairBuffer requestedList) {
        l = requestedList;
    }

    private List<AbstractPair> initOneElementKBs(Collection<NewConditional> nfc, Collection<NewConditional> cnfc) {
        System.out.println("creating 1 element kbs");

        iterationNumberOfKBs = 0;
        List<AbstractPair> listToReturn = new ArrayList<>(cnfc.size());


        //line 3
        for (NewConditional r : cnfc) {

            //line 4 and 5
            AbstractKnowledgeBase rKB = new ObjectKnowledgeBase(iterationNumberOfKBs);
            rKB.add(r); // rKB is r as 1 element kb
            List<NewConditional> D = new ArrayList<>();

            for (NewConditional d : cnfc) {
                if (d.getEqConditionalsList().get(0).getNumber() < r.getNumber()) {
                    D.addAll(d.getEqConditionalsList());
                }

            }


            List<NewConditional> conditionalsToAdd = new ArrayList<>(nfc);
            conditionalsToAdd.removeAll(D);
            conditionalsToAdd.remove(r.getCounterConditional());
            conditionalsToAdd.remove(r);

            //this is another implementation for initializing the pairs
            //same speed as version above
 /*           List<NewConditional> conditionalsToAdd = new ArrayList<>();
            for (NewConditional nfcElement : nfc) {
                if (!D.contains(nfcElement))
                    if (!nfcElement.equals(r))
                        if (!nfcElement.equals(r.getCounterConditional()))
                            conditionalsToAdd.add(nfcElement);
            }*/


            //no buffering for first iteration because it almost makes no difference
            listToReturn.add(new RealListPair(rKB, conditionalsToAdd));
            iterationNumberOfKBs++;
            nextCandidatePairAmount++;

        }

        for (AbstractPair candidatePair : listToReturn)
            kbWriter.addConsistentKb(candidatePair.getKnowledgeBase());

        System.out.println("finished 1 element kbs");
        return listToReturn;
    }


    public int getTotalKbAmount() {
        return totalNumberOfKBs;
    }

    public int getTotalInconsistentAmount() {
        return totalInconsistentAmount;
    }

    public int getIterationNumberOfKBs() {
        return iterationNumberOfKBs;
    }

    public CreatorStatus getCreatorStatus() {
        return creatorStatus;
    }

    public void stop() {
        this.creatorStatus = CreatorStatus.STOPPED;
        kbWriter.stop();
    }

    public int getCurrentK() {
        return k;
    }

    public int getNextCandidatePairAmount() {
        return nextCandidatePairAmount;
    }


    public AbstractKbWriter getKbWriterThread() {
        return kbWriter;
    }

    public AbstractPairBuffer getPairBuffer() {
        return l;
    }

    public void waitForKbWriter() {
        creatorStatus = CreatorStatus.WAITING_FOR_WRITER;
        waitForKbWriter = true;
    }

    private float calculateProgress(int pairCounter, int lastIterationAmount) {

        //avoid division with zero
        if (lastIterationAmount == 0) {
            return 0;

        }
        return (pairCounter / (float) lastIterationAmount) * 100;
    }

    public float getProgress() {
        return progress;
    }

    public int getLastPairAmount() {
        return lastIterationAmount;
    }

    public long getStartTime() {
        return startTime;
    }

    public enum CreatorStatus {

        NOT_STARTED, CREATING_CONDITIONALS, RUNNING, FINISHED, STOPPED, WAITING_FOR_WRITER
    }

}




