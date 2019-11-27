package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealPair;
import kb_creator.model.propositional_logic.AbstractFormula;
import kb_creator.model.propositional_logic.KnowledgeBase;
import kb_creator.model.propositional_logic.PConditional;
import kb_creator.model.propositional_logic.signature.AbstractSignature;
import kb_creator.model.writer.AbstractKbWriter;
import kb_creator.model.writer.KbDummyWriter;
import kb_creator.model.writer.KbFileWriter;
import nfc_creator.model.NfcCreator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class AbstractCreator implements Runnable {


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


    public AbstractCreator(AbstractSignature signature, String kbFilePath, AbstractPairBuffer l) {
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


    public void stopLoop() {
        kbWriter.stopThreads();
        this.creatorStatus = CreatorStatus.STOPPED;
    }

    public void finishAndStopLoop() {
        kbWriter.finishAndStopThreads();
        this.creatorStatus = CreatorStatus.FINISHED;
    }


    protected void waitForWriterFinished() {
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

    public abstract int getTotalInconsistentAmount();

    public abstract float calculateProgress();

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
