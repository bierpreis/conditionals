package kb_creator.model.conditionals;

import kb_creator.model.conditionals.pairs.CandidateNumbersArrayPair;
import kb_creator.model.conditionals.pairs.CompressedCandidateArrayPair;
import kb_creator.model.conditionals.pairs.RealCompressedListPair;
import kb_creator.observer.Status;
import kb_creator.model.conditionals.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.conditionals.knowledge_base.ObjectKnowledgeBase;
import kb_creator.model.conditionals.buffer.AbstractPairBuffer;
import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.propositional_logic.Signature.AbstractSignature;
import kb_creator.model.kb_writer.AbstractKbWriter;
import kb_creator.model.kb_writer.KbDummyWriter;
import kb_creator.model.kb_writer.KbFileWriter;
import nfc.model.NfcCreator;

import java.util.*;

public class KBCreator implements Runnable {

    private int totalNumberOfKBs;
    private int totalInconsistentAmount;
    private int iterationNumberOfKBs;

    private volatile Status status;
    private volatile boolean waitForKbWriter;

    private AbstractSignature signature;

    private int k;
    private int nextCandidatePairAmount;

    private long startTime;


    private AbstractKbWriter kbWriter;

    private AbstractPairBuffer l;

    private float progress;


    public KBCreator(AbstractSignature signature, String kbFilePath) {
        System.out.println("new kb creator");

        status = Status.NOT_STARTED;
        this.signature = signature;
        waitForKbWriter = false;

        if (kbFilePath != null)
            kbWriter = new KbFileWriter(kbFilePath);
        else kbWriter = new KbDummyWriter();

    }


    @Override
    public void run() {
        System.out.println("creator thread started");
        status = Status.CREATING_CONDITIONALS;

        startTime = System.currentTimeMillis();

        Thread kbWriterThread = new Thread(kbWriter);
        kbWriterThread.start();

        NfcCreator nfcCreator = new NfcCreator(signature);

        status = Status.RUNNING;


        k = 1;
        //add empty list to l because java buffer start at 0 and original algorithm starts list at 1
        //then k and k+1 values are the same here and in the original algorithm
        l.addNewList(new ArrayList<>(0));

        Collection<NewConditional> nfc = Collections.unmodifiableCollection(nfcCreator.getNewNfc());

        Map nfcMap = Collections.unmodifiableMap(createNfcMap(nfc));

        Collection<NewConditional> cnfc = Collections.unmodifiableCollection(nfcCreator.getNewCnfc());

        AbstractPair.setNfc(nfcMap);
        AbstractKnowledgeBase.setNfcMap(nfcMap);

        l.addNewList(initOneElementKBs(nfc, cnfc));

        l.finishIteration(k);
        l.prepareIteration(k);

        //the following is the actual loop where the work is done

        //line 6
        while (l.hasElementsForK(k)) {
            System.gc();

            nextCandidatePairAmount = 0;

            //line  7
            l.addNewList(new ArrayList<>());


            iterationNumberOfKBs = 0;
            //this loop is line 8

            int pairCounter = 0;
            while (l.hasMoreElements(k)) {
                AbstractPair candidatePair = l.getNextPair(k);
                pairCounter++;


                progress = calculateProgress(pairCounter, l.getLastIterationPairAmount());
                //line 9
                for (NewConditional r : candidatePair.getCandidatesList()) {
                    //line 10 //
                    if (candidatePair.getKnowledgeBase().isConsistent(r)) {
                        //next part is line 11 and 12
                        //first create the new knowledge base

                        AbstractKnowledgeBase knowledgeBaseToAdd = new ObjectKnowledgeBase(signature, iterationNumberOfKBs);
                        knowledgeBaseToAdd.add(candidatePair.getKnowledgeBase()); //add R to new ObjectKnowledgeBase
                        knowledgeBaseToAdd.add(r); // add r to new ObjectKnowledgeBase

                        kbWriter.addConsistentKb(knowledgeBaseToAdd);

                        //then create candidates
                        List<NewConditional> candidatesToAdd = new ArrayList<>();
                        for (NewConditional conditionalFromCandidates : candidatePair.getCandidatesList())
                            if (conditionalFromCandidates.getNumber() > r.getNumber() && !conditionalFromCandidates.equals(r.getCounterConditional()))
                                candidatesToAdd.add(conditionalFromCandidates);

                        //line 12

                        //this is where the ram gets full. therefore the buffering
                        l.addPair(new RealCompressedListPair(knowledgeBaseToAdd, candidatesToAdd));


                        nextCandidatePairAmount++;

                        iterationNumberOfKBs++;
                        totalNumberOfKBs++;


                    } else {
                        AbstractKnowledgeBase inconsistentKB = new ObjectKnowledgeBase(signature, iterationNumberOfKBs);
                        inconsistentKB.add(candidatePair.getKnowledgeBase());
                        inconsistentKB.add(r);
                        kbWriter.addInconsistentKb(inconsistentKB);
                        totalInconsistentAmount++;
                    }
                }

                //todo: is this still needed?
                if (waitForKbWriter)
                    synchronized (this) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        waitForKbWriter = false;
                    }

                if (status.equals(Status.STOPPED)) {
                    return;
                }
                //delete to save some memory
                candidatePair.deleteCandidates();

                //delete written candidates to save memory

                candidatePair.deleteKB();
            }
            l.finishIteration(k);
            k = k + 1;
            l.prepareIteration(k);

            l.clear(k - 1);
        }
        l.stopThread();
        status = Status.FINISHED;
    }

    public void setSignature(AbstractSignature signature) {
        this.signature = signature;
    }


    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            l.add(new RealCompressedListPair(rKB, conditionalsToAdd));
            iterationNumberOfKBs++;
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

    public Status getStatus() {
        return status;
    }

    public void stop() {
        this.status = Status.STOPPED;
    }

    public int getCurrentK() {
        return k;
    }

    public int getNextCandidatePairAmount() {
        return nextCandidatePairAmount;
    }

    private Map<Integer, NewConditional> createNfcMap(Collection<NewConditional> nfc) {
        Map<Integer, NewConditional> conditionalMap = new HashMap<>();
        for (NewConditional conditional : nfc) {
            if (conditionalMap.containsKey(conditional.getNumber())) {
                throw new RuntimeException("Double conditional detected!");
            }
            conditionalMap.put(conditional.getNumber(), conditional);
        }

        return conditionalMap;
    }

    private Map<Integer, NewConditional> createCnfcMap(List<NewConditional> cnfc) {
        Map<Integer, NewConditional> conditionalMap = new HashMap<>();
        for (NewConditional conditional : cnfc) {
            conditionalMap.put(conditional.getNumber(), conditional);
        }

        return conditionalMap;
    }

    public AbstractKbWriter getKbWriterThread() {
        return kbWriter;
    }

    public AbstractPairBuffer getPairBuffer() {
        return l;
    }

    public void waitForKbWriter() {
        status = Status.WAITING_FOR_WRITER;
        waitForKbWriter = true;
    }

    private float calculateProgress(int pairCounter, int lastIterationAmount) {
        if (lastIterationAmount == 0)
            return 0;
        return (float) (pairCounter / (float) lastIterationAmount) * 100;
    }

    public float getProgress() {
        return progress;
    }

    public int getLastPairAmount() {
        return l.getLastIterationPairAmount();
    }

    public long getStartTime() {
        return startTime;
    }

}
