package kb_creator.model;

import kb_creator.Observer.KBCreatorObserver;
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
    //todo: remove?
    private KBCreatorObserver observer;

    private volatile Status status;

    private String signature;

    public KBCreator(KBCreatorObserver observer) {
        System.out.println("new kb creator");
        this.observer = observer;

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


        for (CandidatePair candidatePair : candidatePairs) { //this loop is line 8
            for (Conditional candidate : candidatePair.getCandidates()) { //this is line 9

                if (checkConsistency(candidatePair.getKnowledgeBase(), candidate)) {
                    //todo: add here to kbs
                    knowledgeBaseCounter++;
                    alreadyFinishedCalculations++;
                }


                if (status.equals(Status.PAUSE)) {
                    sleep(500);
                    continue;
                } else if (status.equals(Status.STOPPED))
                    break;

            }


        }
        status = Status.FINISHED;


    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    private boolean checkConsistency(List<Conditional> knowledgeBase, Conditional candidate) {
        //todo this test is written in goldszmit/pearl 1996 p 65
        //siehe auch infofc s 4 dazu. auch s 9 dort.


        //this sleep is placeholder. remove when implement sth useful here
        sleep(1);
        return true;
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
        List<CandidatePair> candidatePairs = new LinkedList<>(); //candidate pairs is l in original
        for (Conditional cNfcElement : cnfc) { // cNfcElement is r in original
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

}
