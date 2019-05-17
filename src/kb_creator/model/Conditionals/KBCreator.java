package kb_creator.model.Conditionals;

import kb_creator.Observer.Status;
import kb_creator.model.Signature.AbstractSignature;
import nfc.model.NfcCreator;

import java.util.LinkedList;
import java.util.List;

public class KBCreator implements Runnable {

    private volatile int knowledgeBaseCounter;
    private volatile int candidatePairAmount;

    private volatile double totalNumberOfCalculations;
    private volatile double alreadyFinishedCalculations;


    private volatile Status status;

    private AbstractSignature signature;

    private List<KnowledgeBase> kbList;
    private int k;

    public KBCreator(AbstractSignature signature) {
        kbList = new LinkedList<>();

        totalNumberOfCalculations = 0;
        alreadyFinishedCalculations = 0;

        status = Status.NOT_STARTED;
        this.signature = signature;
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

        List<List<CandidatePair>> l = new LinkedList<>();

        List<NewConditional> nfc = nfcCreator.getNewNfc();
        List<NewConditional> cnfc = nfcCreator.getNewCnfc();
        System.out.println("creating one element knowledgeBases");
        l.add(initOneElementKBs(nfc, cnfc));


        //the following is the actual loop where the work is done


        while (!l.get(k).isEmpty()) { //line 6
            l.add(new LinkedList<>()); //line  7
            for (CandidatePair candidatePair : l.get(k)) { //this loop is line 8
                for (NewConditional r : candidatePair.getCandidates()) { //this is line 9
                    if (candidatePair.getKnowledgeBase().isConsistent(r)) { //line 10
                        KnowledgeBase knowledgeBaseToAdd = new KnowledgeBase(signature);
                        knowledgeBaseToAdd.add(candidatePair.getKnowledgeBase()); //add R to new KnowledgeBase
                        knowledgeBaseToAdd.add(r); // add r to new KnowledgeBase //todo: this is the concistent kb. why dont add it to real kbs?
                        //knowledgeBaseToAdd.sort(); ??

                        List<NewConditional> candidatesToAdd = new LinkedList<>();
                        for (NewConditional conditional : candidatePair.getCandidates())
                            if (conditional.getNumber() > r.getNumber() && !conditional.equals(r.getCounterConditional()))
                                candidatesToAdd.add(conditional);


                        l.get(k + 1).add(new CandidatePair(knowledgeBaseToAdd, candidatesToAdd));

                        //todo: why 2 "same" variables?
                        alreadyFinishedCalculations++;
                        knowledgeBaseCounter++;
                    }
                    while (status.equals(Status.PAUSE))
                        sleep(500);
                    if (status.equals(Status.STOPPED)) {
                        return;

                    }

                }

            }
            k = k + 1;
            if (k == 1)
                System.out.println(l.get(0));
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
        candidatePairAmount = 0;
        List<CandidatePair> l = new LinkedList<>();
        for (NewConditional r : cnfc) { //line 3 in original

            KnowledgeBase rKB = new KnowledgeBase(signature); //line 4 and 5
            rKB.add(r); // rKB is r as 1 element kb
            List<NewConditional> conditionalsToAdd = new LinkedList<>();
            for (NewConditional conditional : nfc)
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

    public void stop() {
        status = Status.STOPPED;
    }

    public void pause(boolean pause) {

        if (pause)
            status = Status.PAUSE;
        if (!pause)
            status = Status.RUNNING;

    }

    public int getK() {
        return k;
    }


}
