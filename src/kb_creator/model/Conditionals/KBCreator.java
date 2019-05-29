package kb_creator.model.Conditionals;

import kb_creator.Observer.Status;
import kb_creator.model.Signature.AbstractSignature;
import nfc.model.NfcCreator;

import java.io.File;
import java.util.*;

public class KBCreator implements Runnable {

    private volatile int totalNumberOfKBs;
    private volatile int iterationNumberOfKBs;

    private volatile Status status;

    private AbstractSignature signature;

    private int k;
    private int candidatePairAmount;
    private int nextCandidatePairAmount;

    //todo: writer should not be public but created in method iterations
    private FileWriter fileWriter;

    private String filePath;

    public KBCreator(AbstractSignature signature, String filePath) {
        System.out.println("new kb creator");

        status = Status.NOT_STARTED;
        this.signature = signature;
        this.filePath = filePath;
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

            //todo: here new FileWriter
            System.out.println("creating " + k + "element kbs");

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
                        for (NewConditional conditional : candidatePair.getCandidatesList())
                            if (conditional.getNumber() > r && !conditional.equals(candidate.getCounterConditional()))
                                candidatesToAdd.add(conditional);

                        //line 12

                        //todo: massive problem is here: huge amounts of cp are created (with abc there are 1 kb and 6k candidates for each...)
                        l.get(k + 1).add(new CandidatePair(knowledgeBaseToAdd, candidatesToAdd));


                        nextCandidatePairAmount++;

                        iterationNumberOfKBs++;
                        totalNumberOfKBs++;

                        //todo: inconsistent kbs get useless numbers. what to do with this?
                        //it should write r to there also
                    } else {
                        KnowledgeBase inconsistentKB = new KnowledgeBase(signature, iterationNumberOfKBs);
                        inconsistentKB.add(candidatePair.getKnowledgeBase());
                        inconsistentKB.add(r);
                        fileWriter.writeInconsistentKBToFile(inconsistentKB);
                    }
                }

                while (status.equals(Status.PAUSE))
                    sleep(500);
                if (status.equals(Status.STOPPED)) {
                    return;
                }

                //delete old candidates to save some memory
                candidatePair.deleteCandidates();

                //todo: could this not be in inner loop??
                //comment the following out for testing
                fileWriter.writeConsistentKBToFile(candidatePair.getKnowledgeBase());

                //delete written candidates to save memory
                candidatePair.deleteKB();
            }
            k = k + 1;

            //System.out.println(l.get(0));

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
        System.out.println("creating 1 element kbs");
        fileWriter = new FileWriter(filePath, 1);
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
            fileWriter.writeConsistentKBToFile(candidatePair.getKnowledgeBase());
            fileWriter.writeCandidatePair(candidatePair);
        }

        System.out.println("finished 1 element kbs");
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
