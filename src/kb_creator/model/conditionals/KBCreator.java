package kb_creator.model.conditionals;

import kb_creator.observer.Status;
import kb_creator.model.conditionals.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.conditionals.knowledge_base.ObjectKnowledgeBase;
import kb_creator.model.conditionals.pair_lists.AbstractCandidateCollection;
import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.conditionals.pairs.CompressedCandidateArrayPair;
import kb_creator.model.propositional_logic.Signature.AbstractSignature;
import kb_creator.model.cp_buffer.AbstractCPWriter;
import kb_creator.model.kb_writer.AbstractKbWriter;
import kb_creator.model.kb_writer.KbDummyWriter;
import kb_creator.model.kb_writer.KbFileWriter;
import nfc.model.NfcCreator;

import java.util.*;

public class KBCreator implements Runnable {

    private volatile int totalNumberOfKBs;
    private volatile int iterationNumberOfKBs;

    private volatile Status status;
    private volatile boolean waitIsRequested;

    private AbstractSignature signature;

    private int k;
    private int nextCandidatePairAmount;


    private AbstractKbWriter kbWriter;


    private AbstractCandidateCollection l;


    public KBCreator(AbstractSignature signature, String kbFilePath) {
        System.out.println("new kb creator");

        status = Status.NOT_STARTED;
        this.signature = signature;
        waitIsRequested = false;

        if (kbFilePath != null)
            kbWriter = new KbFileWriter(kbFilePath);
        else kbWriter = new KbDummyWriter();

    }


    @Override
    public void run() {
        System.out.println("creator thread started");
        status = Status.CREATING_CONDITIONALS;

        Thread kbWriterThread = new Thread(kbWriter);
        kbWriterThread.start();

        NfcCreator nfcCreator = new NfcCreator(signature);

        status = Status.RUNNING;


        k = 1;
        //add empty list to l because java pair_lists start at 0 and original algorithm starts list at 1
        //then k and k+1 values are the same here and in the original algorithm
        l.addNewList(k, new ArrayList<>(0));

        Collection<NewConditional> nfc = Collections.unmodifiableCollection(nfcCreator.getNewNfc());

        Map nfcMap = Collections.unmodifiableMap(createNfcMap(nfc));

        Collection<NewConditional> cnfc = Collections.unmodifiableCollection(nfcCreator.getNewCnfc());

        AbstractPair.setNfc(nfcMap);
        AbstractKnowledgeBase.setNfcMap(nfcMap);

        l.addNewList(k, initOneElementKBs(nfc, cnfc));


        //the following is the actual loop where the work is done

        l.prepareCollection(k);
        //line 6

        while (l.hasElementsForK(k)) {
            System.gc();

            nextCandidatePairAmount = 0;

            status = Status.WAITING;
            status = Status.RUNNING;
            //line  7
            l.addNewList(k, new ArrayList<>());
            iterationNumberOfKBs = 0;
            //this loop is line 8
            while( l.getNextElement()!=null) {
                AbstractPair candidatePair = l.getNextElement();
                //line 9
                for (NewConditional r : candidatePair.getCandidatesList()) { //todo: null pointer here when k = 6 and candidate pair nr 5
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
                        l.addPair(new CompressedCandidateArrayPair(knowledgeBaseToAdd, candidatesToAdd));


                        nextCandidatePairAmount++;

                        iterationNumberOfKBs++;
                        totalNumberOfKBs++;


                    } else {
                        AbstractKnowledgeBase inconsistentKB = new ObjectKnowledgeBase(signature, iterationNumberOfKBs);
                        inconsistentKB.add(candidatePair.getKnowledgeBase());
                        inconsistentKB.add(r);
                        kbWriter.addInconsistentKb(inconsistentKB);
                    }
                }

                if (waitIsRequested)
                    synchronized (this) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        waitIsRequested = false;
                    }
                status = Status.RUNNING;

                while (status.equals(Status.PAUSE))
                    sleep(500);
                if (status.equals(Status.STOPPED)) {
                    return;
                }

                //delete to save some memory
                candidatePair.deleteCandidates();

                //delete written candidates to save memory

                candidatePair.deleteKB();
            }

            k = k + 1;
            l.prepareCollection(k);
        }

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

    public void setList(AbstractCandidateCollection requestedList) {
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
            l.add(new CompressedCandidateArrayPair(rKB, conditionalsToAdd));
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

    public int getIterationNumberOfKBs() {
        return iterationNumberOfKBs;
    }

    public Status getStatus() {
        return status;
    }

    public void stop() {
        status = Status.STOPPED;
    }

    public void pause(boolean pause) {

        if (pause)
            status = Status.PAUSE;
        if (!pause)
            status = Status.RUNNING;

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

    public AbstractCPWriter getCpWriterThread() {
        return l.getCpWriter();
    }

    public void setWaiting() {
        status = Status.WAITING;
        waitIsRequested = true;
    }


    public void stopWaiting() {
        status = Status.RUNNING;
        super.notify();

    }


}
