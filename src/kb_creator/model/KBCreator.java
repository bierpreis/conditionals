package kb_creator.model;

import kb_creator.Observer.KBCreatorObserver;
import nfc.model.Conditional;

import java.util.LinkedList;
import java.util.List;

public class KBCreator implements Runnable {
    private List<Conditional> nfc; //todo: why is this nerver used??
    private List<Conditional> cNfc;
    private volatile int knowledgeBaseCounter;

    private KBCreatorObserver observer;

    public KBCreator(KBCreatorObserver observer, List<Conditional> nfc, List<Conditional> cNfc) {
        this.observer = observer;
        this.nfc = nfc;
        this.cNfc = cNfc;

    }

    @Override
    public void run() {
        //todo: check if this list is correct. but how?
        List<CandidatePair> candidatePairs = initOneElementKBs();

        for (CandidatePair candidatePair : candidatePairs) { //this loop is line 8
            for (Conditional candidate : candidatePair.getCandidates()) { //this is line 9

                if (checkConsistency(candidatePair.getKnowledgeBase(), candidate))
                    knowledgeBaseCounter++;
            }


        }


    }

    private boolean checkConsistency(List<Conditional> knowledgeBase, Conditional candidate) {
        //todo

        return true;
    }

    public void setNfc(List<Conditional> nfc) {
        this.nfc = nfc;
    }

    public void setcNfc(List<Conditional> cNfc) {
        this.cNfc = cNfc;
    }

    private List<CandidatePair> initOneElementKBs() {
        List<CandidatePair> candidatePairs = new LinkedList<>(); //candidate pairs is l in original
        for (Conditional cNfcElement : cNfc) { // cNfcElement is r in original
            Conditional counterConditional = cNfcElement.getCounterConditional(); //this is not(r)
            List<Conditional> kbToAdd = new LinkedList<>();
            kbToAdd.add(cNfcElement);

            for (Conditional currentConditional : cNfc) { //this loop is line 4 and 5
                List<Conditional> conditionalsToInclude = new LinkedList<>();
                if (currentConditional.getNumber() > cNfcElement.getNumber())  //this removes D from candidates
                    if (!currentConditional.equals(counterConditional))         //this removes not(r) from candidates
                        conditionalsToInclude.add(currentConditional);


                candidatePairs.add(new CandidatePair(kbToAdd, conditionalsToInclude));


            }


        }
        System.out.println("candidate pais size: " + candidatePairs.size());
        return candidatePairs;
    }

    public int getCounter() {
        return knowledgeBaseCounter;
    }
}
