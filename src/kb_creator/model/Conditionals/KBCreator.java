package kb_creator.model.Conditionals;

import kb_creator.Observer.Status;
import kb_creator.model.Conditionals.KnowledgeBase.AbstractKnowledgeBase;
import kb_creator.model.Conditionals.KnowledgeBase.NumbersKnowledgeBase;
import kb_creator.model.Conditionals.KnowledgeBase.ObjectKnowledgeBase;
import kb_creator.model.Conditionals.Lists.AbstractCandidateList;
import kb_creator.model.Signature.AbstractSignature;
import kb_creator.model.Writers.CPWriter;
import kb_creator.model.Writers.KBWriter.AbstractKbWriter;
import kb_creator.model.Writers.KBWriter.DummyWriter;
import kb_creator.model.Writers.KBWriter.KbFileWriter;
import nfc.model.NfcCreator;

import java.util.*;

public class KBCreator implements Runnable {

    private volatile int totalNumberOfKBs;
    private volatile int iterationNumberOfKBs;

    private volatile Status status;

    private AbstractSignature signature;

    private int k;
    private int candidatePairAmount;
    private int nextCandidatePairAmount;

    private CPWriter cpWriter;

    private AbstractKbWriter kbWriter;

    private AbstractCandidateList l;

    public KBCreator(AbstractSignature signature, String filePath) {
        System.out.println("new kb creator");

        status = Status.NOT_STARTED;
        this.signature = signature;

        cpWriter = new CPWriter(filePath);
        if (filePath != null)
            kbWriter = new KbFileWriter(filePath);
        else kbWriter = new DummyWriter();
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
        //add empty list to l because java lists start at 0 and original algorithm starts list at 1
        //then k and k+1 values are the same here and in the original algorithm
        l.getList().add(new ArrayList<>());


        final List<NewConditional> nfc = nfcCreator.getNewNfc();


        final Map<Integer, NewConditional> nfcMap = createNfcMap(nfc);

        final List<NewConditional> cnfc = nfcCreator.getNewCnfc();

        CandidatePair.setNfc(nfcMap);
        AbstractKnowledgeBase.setNfcMap(nfcMap);

        l.getList().add(initOneElementKBs(nfc, cnfc));


        //the following is the actual loop where the work is done

        //line 6
        while (!l.getList().get(k).isEmpty()) {
            nextCandidatePairAmount = 0;
            candidatePairAmount = l.getList().get(k).size();
            //line  7
            l.getList().add(new ArrayList<>());
            iterationNumberOfKBs = 1;
            //this loop is line 8
            for (CandidatePair candidatePair : l.getList().get(k)) {

                //line 9
                for (NewConditional r : candidatePair.getCandidatesList()) {
                    //line 10 //
                    if (candidatePair.getKnowledgeBase().isConsistent(r)) {

                        //next part is line 11 and 12
                        //first create knowledge base
                        //todo: this constructor is used 2 times. this is shit.
                        ObjectKnowledgeBase knowledgeBaseToAdd = new ObjectKnowledgeBase(signature, iterationNumberOfKBs);
                        knowledgeBaseToAdd.add(candidatePair.getKnowledgeBase()); //add R to new ObjectKnowledgeBase
                        knowledgeBaseToAdd.add(r); // add r to new ObjectKnowledgeBase

                        kbWriter.addConsistentKb(knowledgeBaseToAdd);

                        //then create candidates
                        List<NewConditional> candidatesToAdd = new ArrayList<>();
                        for (NewConditional conditionalFromCandidates : candidatePair.getCandidatesList())
                            if (conditionalFromCandidates.getNumber() > r.getNumber() && !conditionalFromCandidates.equals(conditionalFromCandidates.getCounterConditional()))
                                candidatesToAdd.add(conditionalFromCandidates);

                        //line 12

                        //todo: massive problem is here: huge amounts of cp are created (with abc there are 1 kb and 6k candidates for each...)
                        //so write here candidates to file and delete in ram?
                        l.getList().get(k + 1).add(new CandidatePair(knowledgeBaseToAdd, candidatesToAdd));


                        nextCandidatePairAmount++;

                        iterationNumberOfKBs++;
                        totalNumberOfKBs++;


                    } else {
                        //todo: this. how to make this abstract?
                        ObjectKnowledgeBase inconsistentKB = new ObjectKnowledgeBase(signature, iterationNumberOfKBs);
                        inconsistentKB.add(candidatePair.getKnowledgeBase());
                        inconsistentKB.add(r);
                        kbWriter.addInconsistentKb(inconsistentKB);
                    }
                }

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

    public void setList(AbstractCandidateList requestedList) {
        l = requestedList;
    }

    private List<CandidatePair> initOneElementKBs(List<NewConditional> nfc, List<NewConditional> cnfc) {


        System.out.println("creating 1 element kbs");

        iterationNumberOfKBs = 0;
        List<CandidatePair> l = new ArrayList<>();

        //line 3
        for (NewConditional r : cnfc) {

            //line 4 and 5
            AbstractKnowledgeBase rKB = new NumbersKnowledgeBase(signature, iterationNumberOfKBs);
            rKB.add(r); // rKB is r as 1 element kb
            List<NewConditional> conditionalsToAdd = new ArrayList<>();
            for (NewConditional conditional : nfc)
                if (conditional.getNumber() > r.getNumber() && !conditional.equals(r.getCounterConditional()))
                    conditionalsToAdd.add(conditional);
            l.add(new CandidatePair(rKB, conditionalsToAdd));
            iterationNumberOfKBs++;
        }


        for (CandidatePair candidatePair : l) {
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

    public int getCurrentCandidatepairAmount() {
        return candidatePairAmount;
    }

    public int getNextCandidatePairAmount() {
        return nextCandidatePairAmount;
    }

    private Map<Integer, NewConditional> createNfcMap(List<NewConditional> nfc) {
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

    public AbstractKbWriter getWriterThread() {
        return kbWriter;
    }


}
