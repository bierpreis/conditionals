package kb_creator.model;

import kb_creator.Observer.KBCreatorObserver;
import nfc.model.Conditional;

import java.util.LinkedList;
import java.util.List;

public class KBCreator implements Runnable {
    List<Conditional> nfc;
    List<Conditional> cNfc;

    KBCreatorObserver observer;

    public KBCreator(KBCreatorObserver observer, List<Conditional> nfc, List<Conditional> cNfc) {
        this.observer = observer;
        this.nfc = nfc;
        this.cNfc = cNfc;

    }

    @Override
    public void run() {
        //todo: check if this is correct
        List<CandidatePair> candidatePairs = initOneElementKBs();

        for (CandidatePair candidatePair : candidatePairs) {

        }

    }

    public void setNfc(List<Conditional> nfc) {
        this.nfc = nfc;
    }

    public void setcNfc(List<Conditional> cNfc) {
        this.cNfc = cNfc;
    }

    private List<CandidatePair> initOneElementKBs() {
        List<CandidatePair> candidatePairs = new LinkedList<>(); //L
        for (Conditional cNfcElement : cNfc) { // cNfcElement is r.
            List<Conditional> kbToAdd = new LinkedList<>();
            kbToAdd.add(cNfcElement);

            List<Conditional> conditionalsToInclude = new LinkedList<>();
            for (Conditional currentConditional : cNfc) {
                //todo: dont add not(r)
                if (currentConditional.getNumber() > cNfcElement.getNumber()) {
                    conditionalsToInclude.add(currentConditional);


                }

                candidatePairs.add(new CandidatePair(kbToAdd, conditionalsToInclude));


            }


        }

        return candidatePairs;
    }
}
