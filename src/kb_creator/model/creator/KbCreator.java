package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.RealPair;
import kb_creator.model.logic.AbstractFormula;
import kb_creator.model.logic.KnowledgeBase;
import kb_creator.model.logic.PConditional;

import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.logic.signature.AbstractSignature;
import kb_creator.model.writer.AbstractKbWriter;
import kb_creator.model.writer.real.KbFileWriter;
import kb_creator.model.writer.dummy.KbDummyWriter;
import nfc_creator.model.NfcCreator;


import java.util.*;
import java.util.concurrent.BlockingQueue;

public class KbCreator implements Runnable {

    private int currentPairAmount;

    private volatile CreatorStatus creatorStatus;

    protected AbstractSignature signature;

    protected int k;

    private long startTime;

    private Collection<PConditional> nfc;

    private Collection<PConditional> cnfc;


    private AbstractKbWriter kbWriter;


    private BlockingQueue<KnowledgeBase> consistentWriterQueue;
    private BlockingQueue<KnowledgeBase> inconsistentWriterQueue;

    private BlockingQueue<AbstractPair> newIterationQueue;
    private BlockingQueue<AbstractPair> lastIterationQueue;

    private AbstractPairBuffer l;

    private int iterationPairCounter = 0;

    public KbCreator(AbstractSignature signature, String kbFilePath, AbstractPairBuffer l, int requestedFileNameLength, int requestedKbNumber) {
        System.out.println("new simple creator");
        this.newIterationQueue = l.getNextIterationQueue();
        this.lastIterationQueue = l.getLastIterationQueue();

        this.l = l;
        AbstractFormula.setSignature(signature);
        KnowledgeBase.setSignature(signature);


        creatorStatus = CreatorStatus.NOT_STARTED;
        this.signature = signature;

        //kbFilePath is null when no buffering is requested
        if (kbFilePath != null)
            kbWriter = new KbFileWriter(kbFilePath, requestedFileNameLength, requestedKbNumber);
        else
            kbWriter = new KbDummyWriter();

        consistentWriterQueue = kbWriter.getConsistentQueue();

        inconsistentWriterQueue = kbWriter.getInconsistentQueue();

        creatorStatus = CreatorStatus.CREATING_CONDITIONALS;

        NfcCreator nfcCreator = new NfcCreator(signature);

        nfc = Collections.unmodifiableCollection(nfcCreator.getpNfc());
        cnfc = Collections.unmodifiableCollection(nfcCreator.getpCnfc());

        AbstractPair.setNfc(nfcCreator.getNfcMap());
        KnowledgeBase.setNfcMap(nfcCreator.getNfcMap());

        currentPairAmount = 0;

        startTime = System.currentTimeMillis();

    }


    private List<AbstractPair> initOneElementKBs(Collection<PConditional> nfc, Collection<PConditional> cnfc) {
        System.out.println("creating 1 element kbs");

        List<AbstractPair> listToReturn = new ArrayList<>(cnfc.size());
        int numberCounter = 1;

        //line 3
        for (PConditional r : cnfc) {

            //line 4 and 5
            KnowledgeBase rKB = new KnowledgeBase(numberCounter, r);
            numberCounter++;

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
        System.out.println("finished 1 element kbs");
        return listToReturn;
    }

    @Override
    public void run() {
        creatorStatus = CreatorStatus.RUNNING;
        System.out.println("creator thread started");


        //line 2
        k = 1;

        kbWriter.prepareIteration(0); //actually this is iteration 0
        l.prepareIteration(0);

        //line 3-5
        for (AbstractPair pair : initOneElementKBs(nfc, cnfc))
            try {
                newIterationQueue.put(pair);
            } catch (InterruptedException e) {
                //should only be called by stop button in the first microseconds of running the program.
                return;
            }

        kbWriter.finishIteration();
        l.finishIteration(0);


        //line 6
        while (l.hasElementsForIteration(k)) {
            long startTime = System.currentTimeMillis();

            //line  7
            l.prepareIteration(k);
            currentPairAmount = kbWriter.getIterationConsistentCounter();
            kbWriter.prepareIteration(k);
            iterationPairCounter = 0;


            int consistentKbCounter = 1;
            int inconsistentKbCounter = 1;

            //line 8
            while (l.hasMoreElementsForK(k)) {

                AbstractPair currentPair = null;
                try {
                    currentPair = lastIterationQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                iterationPairCounter++;



                //line 9
                for (PConditional r : currentPair.getCandidatesList()) {


                    //line 10
                    if (currentPair.getKnowledgeBase().isConsistentWith(r)) { //takes almost no time


                        //next part is line 11 and 12
                        KnowledgeBase knowledgeBaseToAdd = new KnowledgeBase(consistentKbCounter, currentPair.getKnowledgeBase(), r); //takes little time
                        consistentKbCounter++;


                        try {
                            consistentWriterQueue.put(knowledgeBaseToAdd);
                        } catch (InterruptedException e) {
                            //this can only triggered by stop button in gui
                            return;
                        }

                        List<PConditional> candidatesToAdd = new ArrayList<>();
                        for (PConditional conditionalFromCandidates : currentPair.getCandidatesList()) //loop takes most of the time (70 percent)
                            if (conditionalFromCandidates.getNumber() > r.getNumber() && !conditionalFromCandidates.equals(r.getCounterConditional())) //equals is faster then comparing numbers here.
                                candidatesToAdd.add(conditionalFromCandidates);


                        //line 12
                        try {
                            newIterationQueue.put(new RealPair(knowledgeBaseToAdd, candidatesToAdd));
                        } catch (InterruptedException e) {
                            //this should only be triggered by gui stop button
                            return;
                        }


                    } else {
                        try {
                            inconsistentWriterQueue.put(new KnowledgeBase(inconsistentKbCounter, currentPair.getKnowledgeBase(), r));
                        } catch (InterruptedException e) {
                            //this can and should ONLY be triggered by gui stop button
                            return;
                        }
                        inconsistentKbCounter++; //counter is only for kb constructor
                    }
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


    //iteration change methods

    public void stopLoop() {
        kbWriter.stopThreads();
        this.creatorStatus = CreatorStatus.STOPPED;
    }

    private void finishAndStopLoop() {
        kbWriter.finishAndStopThreads();
        this.creatorStatus = CreatorStatus.FINISHED;
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


}



