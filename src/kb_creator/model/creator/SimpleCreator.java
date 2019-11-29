package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.RealPair;
import kb_creator.model.propositional_logic.AbstractFormula;
import kb_creator.model.propositional_logic.KnowledgeBase;
import kb_creator.model.propositional_logic.PConditional;

import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.propositional_logic.signature.AbstractSignature;
import kb_creator.model.writer.AbstractKbWriter;
import kb_creator.model.writer.KbFileWriter;
import kb_creator.model.writer.dummy.KbDummyWriter;
import nfc_creator.model.NfcCreator;


import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SimpleCreator implements Runnable {

    protected int currentPairAmount;

    protected volatile CreatorStatus creatorStatus;

    protected AbstractSignature signature;

    protected int k;

    protected long startTime;

    protected Collection<PConditional> nfc;

    protected Collection<PConditional> cnfc;

    protected AbstractPairBuffer l;

    protected AbstractKbWriter kbWriter;

    protected BlockingQueue<KnowledgeBase> consistentWriterQueue = new ArrayBlockingQueue<>(500);
    protected BlockingQueue<KnowledgeBase> inconsistentWriterQueue = new ArrayBlockingQueue<>(500);

    protected BlockingQueue<AbstractPair> inputPairsQueue = new ArrayBlockingQueue<>(500);
    protected BlockingQueue<AbstractPair> outputPairsQueue = new ArrayBlockingQueue<>(500);

    private int iterationPairCounter = 0;

    public SimpleCreator(AbstractSignature signature, String kbFilePath, AbstractPairBuffer l) {
        System.out.println("new simple creator");


        this.l = l;
        AbstractFormula.setSignature(signature);
        KnowledgeBase.setSignature(signature);


        creatorStatus = CreatorStatus.NOT_STARTED;
        this.signature = signature;

        //kbFilePath is null when no buffering is requested
        if (kbFilePath != null)
            kbWriter = new KbFileWriter(kbFilePath, consistentWriterQueue, inconsistentWriterQueue);
        else
            kbWriter = new KbDummyWriter(consistentWriterQueue, inconsistentWriterQueue);

        creatorStatus = CreatorStatus.CREATING_CONDITIONALS;

        NfcCreator nfcCreator = new NfcCreator(signature);
        nfc = Collections.unmodifiableCollection(nfcCreator.getpNfc());
        cnfc = Collections.unmodifiableCollection(nfcCreator.getpCnfc());

        AbstractPair.setNfc(nfcCreator.getNfcMap());
        KnowledgeBase.setNfcMap(nfcCreator.getNfcMap());

        currentPairAmount = 0;

        startTime = System.currentTimeMillis();

    }


    protected List<AbstractPair> initOneElementKBs(Collection<PConditional> nfc, Collection<PConditional> cnfc) {
        System.out.println("creating 1 element kbs");

        List<AbstractPair> listToReturn = new ArrayList<>(cnfc.size());

        //line 3
        for (PConditional r : cnfc) {

            //line 4 and 5
            KnowledgeBase rKB = new KnowledgeBase(r);

            //create candidates
            List<PConditional> candidatesList = new ArrayList<>();

            for (PConditional conditional : nfc) {
                if (!(conditional.getEqConditionalsList().get(0).getNumber() < r.getNumber()))
                    if (!(conditional.equals(r)))
                        if (!(conditional.equals(r.getCounterConditional())))
                            candidatesList.add(conditional);

            }


            //no buffering for first iteration because it almost makes no difference
            listToReturn.add(new RealPair(rKB, candidatesList));
        }

        for (AbstractPair candidatePair : listToReturn) {
            try {
                consistentWriterQueue.put(candidatePair.getKnowledgeBase());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        waitForWriterFinished();
        System.out.println("finished 1 element kbs");
        return listToReturn;
    }

    @Override
    public void run() {
        creatorStatus = CreatorStatus.RUNNING;
        System.out.println("creator thread started");
        l.prepareIteration(0);

        //line 2
        k = 1;

        kbWriter.newIteration(0); //actually this is iteration 0

        //line 3-5
        l.addNewList(initOneElementKBs(nfc, cnfc));
        l.finishIteration(0);


        //line 6
        while (l.hasElementsForIteration(k)) {
            long startTime = System.currentTimeMillis();
            System.gc();
            l.prepareIteration(k);
            currentPairAmount = kbWriter.getIterationConsistentCounter();
            kbWriter.newIteration(k);
            iterationPairCounter = 0;


            //line  7
            l.addNewList(new ArrayList<>()); //todo: maybe combine this and l.prepareIteration() some lines before?


            //line 8
            while (l.hasMoreElementsForK(k)) {
                AbstractPair currentPair = l.getNextPair(k);
                iterationPairCounter++;


                //line 9
                for (PConditional r : currentPair.getCandidatesList()) {


                    //line 10
                    if (currentPair.getKnowledgeBase().isConsistentWith(r)) { //takes almost no time


                        //next part is line 11 and 12
                        KnowledgeBase knowledgeBaseToAdd = new KnowledgeBase(currentPair.getKnowledgeBase(), r); //takes little time
                        try {
                            consistentWriterQueue.put(knowledgeBaseToAdd);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        List<PConditional> candidatesToAdd = new ArrayList<>();
                        for (PConditional conditionalFromCandidates : currentPair.getCandidatesList()) //loop takes most of the time (70 percent)
                            if (conditionalFromCandidates.getNumber() > r.getNumber() && !conditionalFromCandidates.equals(r.getCounterConditional())) //equals is faster then comparing numbers here.
                                candidatesToAdd.add(conditionalFromCandidates);

                        //line 12
                        l.addPair(knowledgeBaseToAdd, candidatesToAdd); //this takes about 30 percent of time


                    } else addInconsistentKb(currentPair.getKnowledgeBase(), r); //takes almost no time
                }
                if (creatorStatus.equals(CreatorStatus.STOPPED))
                    return;
                currentPair.clear(); //saves a lot of memory and takes almost no time

            }
            System.out.println("time for iteration " + k + ": " + (System.currentTimeMillis() - startTime) / 1000 + "s");
            //line 13
            kbWriter.finishIteration();
            l.finishIteration(k);
            k = k + 1;
        }
        creatorStatus = CreatorStatus.FINISHED;
        finishAndStopLoop();
    }

    private void addInconsistentKb(KnowledgeBase knowledgeBase, PConditional conditionalToAdd) {
        try {
            inconsistentWriterQueue.put(new KnowledgeBase(knowledgeBase, conditionalToAdd));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getTotalInconsistentAmount() {
        return kbWriter.getTotalInconsistentCounter();
    }


    public float calculateProgress() {

        //avoid division with zero
        if (currentPairAmount == 0) {
            return 0;

        }
        return (iterationPairCounter / (float) currentPairAmount) * 100;
    }

    public void stopLoop() {
        kbWriter.stopThreads();
        this.creatorStatus = CreatorStatus.STOPPED;
    }

    public void finishAndStopLoop() {
        kbWriter.finishAndStopThreads();
        this.creatorStatus = CreatorStatus.FINISHED;
    }


    protected void waitForWriterFinished() { //todo: this is only used in iteration 0! why? delete? use it in every iteration?
        while (!consistentWriterQueue.isEmpty())
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    //getters

    public AbstractPairBuffer getPairBuffer() {
        return l;
    }

    public AbstractKbWriter getKbWriterThread() {
        return kbWriter;
    }

    public int getCurrentK() {
        return k;
    }

    public int getTotalKbAmount() {
        return kbWriter.getTotalConsistentCounter();
    }


    public CreatorStatus getCreatorStatus() {
        return creatorStatus;
    }

    public int getCurrentPairAmount() {
        return currentPairAmount;
    }

    public long getStartTime() {
        return startTime;
    }

}




