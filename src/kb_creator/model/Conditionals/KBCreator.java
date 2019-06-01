package kb_creator.model.Conditionals;

import kb_creator.Observer.Status;
import kb_creator.model.Signature.AbstractSignature;
import kb_creator.model.Writers.CPWriter;
import kb_creator.model.Writers.KBWriter;
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

    private String filePath;

    private CPWriter cpWriter;

    public KBCreator(AbstractSignature signature, String filePath) {
        System.out.println("new kb creator");

        status = Status.NOT_STARTED;
        this.signature = signature;
        this.filePath = filePath;

        cpWriter = new CPWriter(filePath);
    }


    @Override
    public void run() {
        System.out.println("creator thread started");
        status = Status.CREATING_CONDITIONALS;


        NfcCreator nfcCreator = new NfcCreator(signature);

        status = Status.RUNNING;

        //k in original paper starts at 1
        //here it starts at 0 because lists in java start at 0 and not 1
        k = 0;

        List<List<CandidatePair>> l = new ArrayList<>();

        final List<NewConditional> nfc = nfcCreator.getNewNfc();
        final Map<Integer, NewConditional> nfcMap = createNfcMap(nfc);

        final List<NewConditional> cnfc = nfcCreator.getNewCnfc();
        //Map<Integer, NewConditional> cnfcMap = createCnfcMap(cnfc);

        CandidatePair.setNfc(nfcMap);
        KnowledgeBase.setNfcMap(nfcMap);

        l.add(initOneElementKBs(nfc, cnfc));


        //the following is the actual loop where the work is done

        //line 6
        while (!l.get(k).isEmpty()) {
            nextCandidatePairAmount = 0;
            candidatePairAmount = l.get(k).size();
            KBWriter KBWriter = new KBWriter(filePath, k + 1);

            //line  7
            l.add(new ArrayList<>());
            iterationNumberOfKBs = 1;
            //this loop is line 8
            for (CandidatePair candidatePair : l.get(k)) {

                //line 9
                for (Integer r : candidatePair.getCandidatesNumbersList()) {
                    NewConditional candidate = nfcMap.get(r);
                    //line 10 //
                    if (candidatePair.getKnowledgeBase().isConsistent(candidate)) {

                        //next part is line 11 and 12
                        //first create knowledge base
                        KnowledgeBase knowledgeBaseToAdd = new KnowledgeBase(signature, iterationNumberOfKBs);
                        knowledgeBaseToAdd.add(candidatePair.getKnowledgeBase()); //add R to new KnowledgeBase
                        knowledgeBaseToAdd.add(r); // add r to new KnowledgeBase

                        //then create candidates
                        List<NewConditional> candidatesToAdd = new ArrayList<>();
                        for (NewConditional conditionalFromCandidates : candidatePair.getCandidatesList())
                            if (conditionalFromCandidates.getNumber() > r && !conditionalFromCandidates.equals(conditionalFromCandidates.getCounterConditional()))
                                candidatesToAdd.add(conditionalFromCandidates);

                        //line 12

                        //todo: massive problem is here: huge amounts of cp are created (with abc there are 1 kb and 6k candidates for each...)
                        //so write here candidates to file and delete in ram?
                        l.get(k + 1).add(new CandidatePair(knowledgeBaseToAdd, candidatesToAdd));


                        nextCandidatePairAmount++;

                        iterationNumberOfKBs++;
                        totalNumberOfKBs++;


                        //it should write r to there also
                    } else {
                        //todo: not sure if this writer k+2 is a good idea
                        KBWriter inconsistentKBWriter = new KBWriter(filePath, k + 2);
                        KnowledgeBase inconsistentKB = new KnowledgeBase(signature, iterationNumberOfKBs);
                        inconsistentKB.add(candidatePair.getKnowledgeBase());
                        inconsistentKB.add(r);
                        inconsistentKBWriter.writeInconsistentKBToFile(inconsistentKB);
                    }
                }

                while (status.equals(Status.PAUSE))
                    sleep(500);
                if (status.equals(Status.STOPPED)) {
                    return;
                }

                //todo: could this and next not be in inner loop??
                //todo: all pairs get written. even the incosistent?
                //this gets written when candidatepair+1 is finished.
                cpWriter.writePair(candidatePair);

                //delete to save some memory
                candidatePair.deleteCandidates();


                KBWriter.writeConsistentKBToFile(candidatePair.getKnowledgeBase());

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

    private List<CandidatePair> initOneElementKBs(List<NewConditional> nfc, List<NewConditional> cnfc) {
        KBWriter KBWriter = new KBWriter(filePath, 1);
        System.out.println("creating 1 element kbs");

        iterationNumberOfKBs = 0;
        List<CandidatePair> l = new ArrayList<>();

        //line 3
        for (NewConditional r : cnfc) {

            //line 4 and 5
            KnowledgeBase rKB = new KnowledgeBase(signature, iterationNumberOfKBs);
            rKB.add(r.getNumber()); // rKB is r as 1 element kb
            List<NewConditional> conditionalsToAdd = new ArrayList<>();
            for (NewConditional conditional : nfc)
                if (conditional.getNumber() > r.getNumber() && !conditional.equals(r.getCounterConditional()))
                    conditionalsToAdd.add(conditional);
            l.add(new CandidatePair(rKB, conditionalsToAdd));
            iterationNumberOfKBs++;
        }


        for (CandidatePair candidatePair : l) {
            KBWriter.writeConsistentKBToFile(candidatePair.getKnowledgeBase());
            cpWriter.writePair(candidatePair);
        }

        System.out.println("finished 1 element kbs");
        cpWriter.deleteFiles(1);
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


}
