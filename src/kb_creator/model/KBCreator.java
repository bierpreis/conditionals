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

                sleep(1);//todo replace with concistecy check
                //here check consistency if (checkConsistency(candidatePair.getKnowledgeBase(), candidate)) {
                //todo: add here to kbs. 
                knowledgeBaseCounter++;
                alreadyFinishedCalculations++;


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
        List<CandidatePair> candidatePairs = new LinkedList<>(); //candidate pairs is L in original
        for (Conditional cNfcElement : cnfc) { // cNfcElement is r in original, line 3 in original
            Conditional counterConditional = cNfcElement.getCounterConditional(); //this is not(r)
            List<Conditional> kbToAdd = new LinkedList<>();
            kbToAdd.add(cNfcElement); //this is addring {r} in line 5
            List<Conditional> conditionalsToInclude = new LinkedList<>();

            for (Conditional currentConditional : nfc) { //this loop is line 4 and 5
                if (currentConditional.getNumber() > cNfcElement.getNumber())  //this removes D from candidates
                    if (!currentConditional.equals(counterConditional)) {      //this removes not(r) from candidates
                        conditionalsToInclude.add(currentConditional);
                    }


            }
            candidatePairs.add(new CandidatePair(kbToAdd, conditionalsToInclude));
            candidatePairAmount++;
        }
        System.out.println("candidate pairs:" + candidatePairs.size());
        return candidatePairs;
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
