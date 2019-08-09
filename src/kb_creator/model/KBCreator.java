package kb_creator.model;

import kb_creator.model.pairs.CompressedArrayPair;
import kb_creator.model.pairs.RealListPair;
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

    private boolean isBufferingActive;


    public KBCreator(AbstractSignature signature, String kbFilePath) {
        System.out.println("new kb creator");

        creatorStatus = CreatorStatus.NOT_STARTED;
        this.signature = signature;
        waitForKbWriter = false;

        if (kbFilePath != null) {
            kbWriter = new KbFileWriter(kbFilePath);
            isBufferingActive = true;
        } else {
            kbWriter = new KbDummyWriter();
            isBufferingActive = false;
        }

        lastIterationAmount = 0;

    }

    @Override
    public void run() {

        System.out.println("creator thread started");
        creatorStatus = CreatorStatus.CREATING_CONDITIONALS;

        startTime = System.currentTimeMillis();


        //todo: put as much as possible of this stuff in constructor
        Thread kbWriterThread = new Thread(kbWriter);
        kbWriterThread.start();

        NfcCreator nfcCreator = new NfcCreator(signature);

        creatorStatus = CreatorStatus.RUNNING;


        k = 1;
        //add empty list to l because java buffer start at 0 and original algorithm starts list at 1
        //then k and k+1 values are the same here and in the original algorithm
        l.addNewList(new ArrayList<>(0));


        Collection<NewConditional> nfc = Collections.unmodifiableCollection(nfcCreator.getNewNfc());


        Collection<NewConditional> cnfc = Collections.unmodifiableCollection(nfcCreator.getNewCnfc());

        AbstractPair.setNfc(nfcCreator.getNfcMap());
        AbstractKnowledgeBase.setNfcMap(nfcCreator.getNfcMap());

        l.addNewList(initOneElementKBs(nfc, cnfc));

        l.finishIteration(k);
        l.prepareIteration(k);

        //the following is the actual loop where the work is done

        //line 6
        while (l.hasElementsForK(k)) {
            System.gc();

            int iterationPairCounter = 0;


            lastIterationAmount = nextCandidatePairAmount;
            nextCandidatePairAmount = 0;

            iterationNumberOfKBs = 0;

            //line  7
            l.addNewList(new ArrayList<>());

            //lastIterationAmount = l.getLastIterationPairAmount();

            //this loop is line 8
            while (l.hasMoreElements(k)) {
                long overallStart = System.nanoTime();
                progress = calculateProgress(iterationPairCounter, lastIterationAmount);


                //todo: make sure if ordering is neccesary. if not, threading could be usful. if yes, make sure it is ordered!
                AbstractPair candidatePair = l.getNextPair(k);

                iterationPairCounter++;

                //line 9
                for (NewConditional r : candidatePair.getCandidatesList()) {
                    long candidateStart;
                    //line 10 //
                    //consistency check takes almost no time
                    if (candidatePair.getKnowledgeBase().isConsistent(r)) {


                        //next part is line 11 and 12

                        //first create the new knowledge base


                        AbstractKnowledgeBase knowledgeBaseToAdd = new ObjectKnowledgeBase(signature, iterationNumberOfKBs);

                        knowledgeBaseToAdd.add(candidatePair.getKnowledgeBase());

                        knowledgeBaseToAdd.add(r);

                        kbWriter.addConsistentKb(knowledgeBaseToAdd);


                        //create candidates set
                        //this loop takes most of the time (70 percent)
                        List<NewConditional> candidatesToAdd = new ArrayList<>();
                        for (NewConditional conditionalFromCandidates : candidatePair.getCandidatesList())
                            if (conditionalFromCandidates.getNumber() > r.getNumber() && !conditionalFromCandidates.equals(r.getCounterConditional()))
                                candidatesToAdd.add(conditionalFromCandidates);

                        //line 12
                        //doesn't look great but should be faster then using reflection
                        //this takes about 30 percent of time
                        if (isBufferingActive)
                            l.addPair(new RealListPair(knowledgeBaseToAdd, candidatesToAdd));
                        else l.addPair(new CompressedArrayPair(knowledgeBaseToAdd, candidatesToAdd));


                        nextCandidatePairAmount++;
                        iterationNumberOfKBs++;
                        totalNumberOfKBs++;


                        //save inconsistent knowledge base
                        //this part takes almost no time
                    } else {

                        //todo: own method for this
                        AbstractKnowledgeBase inconsistentKB = new ObjectKnowledgeBase(signature, iterationNumberOfKBs);
                        inconsistentKB.add(candidatePair.getKnowledgeBase());
                        inconsistentKB.add(r);
                        kbWriter.addInconsistentKb(inconsistentKB);
                        totalInconsistentAmount++;

                    }

                }
                checkIfWaitForWriter();

                if (creatorStatus.equals(CreatorStatus.STOPPED)) {
                    return;


                }

                //todo: is this still needed?!
                //delete to save some memory
                candidatePair.deleteCandidates();
                //delete written candidates to save memory
                candidatePair.deleteKB();
                //System.out.println("overall time: " + (System.nanoTime() - overallStart) / 1000);
            }
            l.finishIteration(k);
            k = k + 1;
            l.prepareIteration(k);

            l.clear(k - 1);
        }
        l.stopThread();
        creatorStatus = CreatorStatus.FINISHED;
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
        List<AbstractPair> l = new ArrayList<>();

        //line 3
        for (NewConditional r : cnfc) {
            //line 4 and 5
            AbstractKnowledgeBase rKB = new ObjectKnowledgeBase(signature, iterationNumberOfKBs);
            rKB.add(r); // rKB is r as 1 element kb
            List<NewConditional> conditionalsToAdd = new ArrayList<>();
            for (NewConditional conditional : nfc)
                if (conditional.getNumber() > r.getNumber() && !conditional.equals(r.getCounterConditional()))
                    conditionalsToAdd.add(conditional);

            //no buffereing for first iteration because there is no use for it
            l.add(new RealListPair(rKB, conditionalsToAdd));
            iterationNumberOfKBs++;
            nextCandidatePairAmount++;
        }


        for (AbstractPair candidatePair : l) {
            kbWriter.addConsistentKb(candidatePair.getKnowledgeBase());
            // cpWriter.writePair(candidatePair);
        }


        System.out.println("finished 1 element kbs");
        //cpWriter.deleteFiles(1);
        return l;
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




