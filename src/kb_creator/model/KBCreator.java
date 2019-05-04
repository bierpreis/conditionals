package kb_creator.model;

import kb_creator.Observer.Status;
import nfc.model.Conditional;
import nfc.model.NfcCreator;

import java.util.LinkedList;
import java.util.List;

public class KBCreator implements Runnable {

    private volatile int knowledgeBaseCounter;
    private volatile int candidatePairAmount;

    private volatile double totalNumberOfCalculations;
    private volatile double alreadyFinishedCalculations;


    private volatile Status status;

    private String signature;

    private List<KnowledgeBase> kbList;

    public KBCreator() {
        kbList = new LinkedList<>();

        totalNumberOfCalculations = 0;
        alreadyFinishedCalculations = 0;

        status = Status.NOT_STARTED;
    }


    @Override
    public void run() {
        //todo: make alrorithm as close as original as possible
        status = Status.CREATING_CONDITIONALS;


        NfcCreator nfcCreator = new NfcCreator(signature);

        status = Status.RUNNING;

        List<CandidatePair> candidatePairs = initOneElementKBs(nfcCreator.getNfc(), nfcCreator.getCnfc());


        //this calculates the total number of calculations needed (will be useful for progress info)
        for (CandidatePair candidatePair : candidatePairs)
            for (Conditional candidate : candidatePair.getCandidates())
                totalNumberOfCalculations++;

        //this is the actual loop where the work is done
        for (CandidatePair candidatePair : candidatePairs) { //this loop is line 8
            for (Conditional candidate : candidatePair.getCandidates()) { //this is line 9
                if (candidatePair.getKnowledgeBase().isConsistent(candidate)) {

                    //todo: add here to kbs.
                    knowledgeBaseCounter++;
                    alreadyFinishedCalculations++;

                }
                while (status.equals(Status.PAUSE))
                    sleep(500);
                if (status.equals(Status.STOPPED))
                    break;

            }


        }
        status = Status.FINISHED;


    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<CandidatePair> initOneElementKBs(List<Conditional> nfc, List<Conditional> cnfc) {
        candidatePairAmount = 0;
        List<CandidatePair> l = new LinkedList<>();
        for (Conditional r : cnfc) { //line 3 in original

            KnowledgeBase rKB = new KnowledgeBase(); //line 4 and 5
            rKB.add(r); // rKB is r as 1 element kb
            List<Conditional> conditionalsToAdd = new LinkedList<>();
            for (Conditional conditional : nfc)
                if (conditional.getNumber() > r.getNumber() && !conditional.equals(r.getCounterConditional()))
                    conditionalsToAdd.add(conditional);
            l.add(new CandidatePair(rKB, conditionalsToAdd));
        }
        return l;
    }

    public int getKBAmount() {
        return knowledgeBaseCounter;
    }

    public int getCandidatePairAmount() {
        return candidatePairAmount;
    }

    public Status getStatus() {
        return status;
    }

    public double getProgressInPercent() {
        if (totalNumberOfCalculations != 0)
            return 100 * (alreadyFinishedCalculations / totalNumberOfCalculations);
        else return 0;
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

    public List<KnowledgeBase> getKnowledgeBaseList() {
        return kbList;
    }

}
