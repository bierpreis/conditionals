package kb_creator.model;

import kb_creator.Observer.KBCreatorObserver;
import nfc.model.Conditional;

import java.util.LinkedList;
import java.util.List;

public class KBCreator implements Runnable {
    private List<Conditional> nfc;
    private List<Conditional> cNfc;
    private volatile int knowledgeBaseCounter;
    private volatile int candidatePairAmount;
    private volatile boolean isRunning;

    private volatile double totalNumberOfCalculations;
    private volatile double alreadyFinishedCalculations;

    private KBCreatorObserver observer;
    private volatile boolean stopped;

    public KBCreator(KBCreatorObserver observer, List<Conditional> nfc, List<Conditional> cNfc) {
        this.observer = observer;
        this.nfc = nfc;
        this.cNfc = cNfc;

        totalNumberOfCalculations = 0;
        alreadyFinishedCalculations = 0;
        isRunning = false;
        stopped = false;

    }

    @Override
    public void run() {
        isRunning = true;
        //todo: check if this list is correct. but how?
        List<CandidatePair> candidatePairs = initOneElementKBs();

        //this calculates the total number of calculations needed (will be useful for progress info)
        for (CandidatePair candidatePair : candidatePairs)
            for (Conditional candidate : candidatePair.getCandidates())
                totalNumberOfCalculations++;


        for (CandidatePair candidatePair : candidatePairs) { //this loop is line 8
            for (Conditional candidate : candidatePair.getCandidates()) { //this is line 9
                alreadyFinishedCalculations++;
                if (checkConsistency(candidatePair.getKnowledgeBase(), candidate)) {
                    if (stopped)
                        return;
                    knowledgeBaseCounter++;
                    try {
                        Thread.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        }
        try {
            Thread.sleep(1000); //this is to make sure status thread wont stop too early
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isRunning = false;


    }

    private boolean checkConsistency(List<Conditional> knowledgeBase, Conditional candidate) {
        //todo this test is written in goldszmit/pearl 1996 p 65
        //siehe auch infofc s 4 dazu. auch s 9 dort.

        return true;
    }

    private List<CandidatePair> initOneElementKBs() {
        List<CandidatePair> candidatePairs = new LinkedList<>(); //candidate pairs is l in original
        for (Conditional cNfcElement : cNfc) { // cNfcElement is r in original
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
        return candidatePairs;
    }

    public int getKBAmount() {
        return knowledgeBaseCounter;
    }

    public int getCandidatePairAmount() {
        return candidatePairAmount;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public double getProgressInPercent() {
        if (totalNumberOfCalculations != 0)
            return 100 * (alreadyFinishedCalculations / totalNumberOfCalculations);
        else return 0;
    }

    public void stop() {
        stopped = true;
    }
}
