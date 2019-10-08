package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.knowledge_base.ObjectKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealListPair;
import kb_creator.model.propositional_logic.AbstractFormula;
import kb_creator.model.propositional_logic.NewConditional;
import kb_creator.model.propositional_logic.signature.AbstractSignature;
import kb_creator.model.writer.AbstractKbWriter;
import kb_creator.model.writer.KbDummyWriter;
import kb_creator.model.writer.KbFileWriter;
import nfc.model.NfcCreator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class AbstractCreator implements Runnable {

    protected int totalNumberOfKBs;
    protected int totalInconsistentAmount;
    protected int iterationNumberOfKBs;
    protected int lastIterationAmount;

    protected CreatorStatus creatorStatus;
    protected volatile boolean waitForKbWriter;

    protected AbstractSignature signature;

    protected int k;
    protected int nextCandidatePairAmount;

    protected long startTime;

    protected float progress;

    protected Collection<NewConditional> nfc;

    protected Collection<NewConditional> cnfc;

    protected AbstractPairBuffer l;

    protected AbstractKbWriter kbWriter;


    protected BlockingQueue<AbstractKnowledgeBase> consistentWriterQueue = new ArrayBlockingQueue<>(500);
    protected BlockingQueue<AbstractKnowledgeBase> inconsistentWriterQueue = new ArrayBlockingQueue<>(500);

    protected BlockingQueue<AbstractPair> inputPairsQueue = new ArrayBlockingQueue<>(500);
    protected BlockingQueue<AbstractPair> outputPairsQueue = new ArrayBlockingQueue<>(500);


    public AbstractCreator(AbstractSignature signature, String kbFilePath, AbstractPairBuffer l) {
        this.l = l;
        AbstractFormula.setSignature(signature);
        AbstractKnowledgeBase.setSignature(signature);


        creatorStatus = CreatorStatus.NOT_STARTED;
        this.signature = signature;

        //kbFilePath is null when no buffering is requested
        if (kbFilePath != null)
            kbWriter = new KbFileWriter(kbFilePath, consistentWriterQueue, inconsistentWriterQueue);
        else
            kbWriter = new KbDummyWriter(consistentWriterQueue, inconsistentWriterQueue);

        creatorStatus = CreatorStatus.CREATING_CONDITIONALS;

        NfcCreator nfcCreator = new NfcCreator(signature);
        nfc = Collections.unmodifiableCollection(nfcCreator.getNewNfc());
        cnfc = Collections.unmodifiableCollection(nfcCreator.getNewCnfc());

        AbstractPair.setNfc(nfcCreator.getNfcMap());
        AbstractKnowledgeBase.setNfcMap(nfcCreator.getNfcMap());

        System.out.println("new kb creator");

        lastIterationAmount = 0;

        startTime = System.currentTimeMillis();
    }

    public void setSignature(AbstractSignature signature) {
        this.signature = signature;
    }

    public float getProgress() {
        return progress;
    }

    public int getLastPairAmount() {
        return lastIterationAmount;
    }

    public long getStartTime() {
        return startTime;
    }

    public enum CreatorStatus {

        NOT_STARTED, CREATING_CONDITIONALS, RUNNING, FINISHED, STOPPED, WAITING_FOR_WRITER
    }

    protected float calculateProgress(int pairCounter, int lastIterationAmount) {

        //avoid division with zero
        if (lastIterationAmount == 0) {
            return 0;

        }
        return (pairCounter / (float) lastIterationAmount) * 100;
    }

    //todo: should be deleted when writer has blocking queue
    public void waitForKbWriter() {
        creatorStatus = CreatorStatus.WAITING_FOR_WRITER;
        waitForKbWriter = true;
    }

    public int getIterationNumberOfKBs() {
        return iterationNumberOfKBs;
    }

    public CreatorStatus getCreatorStatus() {
        return creatorStatus;
    }

    public void stop() {
        kbWriter.stop();
        this.creatorStatus = CreatorStatus.STOPPED;
    }

    public int getCurrentK() {
        return k;
    }

    public int getNextCandidatePairAmount() {
        return nextCandidatePairAmount;
    }

    public int getTotalKbAmount() {
        return totalNumberOfKBs;
    }

    public int getTotalInconsistentAmount() {
        return totalInconsistentAmount;
    }

    protected List<AbstractPair> initOneElementKBs(Collection<NewConditional> nfc, Collection<NewConditional> cnfc) {
        System.out.println("creating 1 element kbs");

        iterationNumberOfKBs = 0;
        List<AbstractPair> listToReturn = new ArrayList<>(cnfc.size());


        //line 3
        for (NewConditional r : cnfc) {

            //line 4 and 5
            AbstractKnowledgeBase rKB = new ObjectKnowledgeBase(iterationNumberOfKBs);
            rKB.add(r); // rKB is r as 1 element kb

            //create candidates
            List<NewConditional> candidatesList = new ArrayList<>();

            for (NewConditional conditional : nfc) {
                if (!(conditional.getEqConditionalsList().get(0).getNumber() < r.getNumber()))
                    if (!(conditional.equals(r)))
                        if (!(conditional.equals(r.getCounterConditional())))
                            candidatesList.add(conditional);

            }


            //no buffering for first iteration because it almost makes no difference
            listToReturn.add(new RealListPair(rKB, candidatesList));
            iterationNumberOfKBs++;
            nextCandidatePairAmount++;

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

    public AbstractPairBuffer getPairBuffer() {
        return l;
    }

    public AbstractKbWriter getKbWriterThread() {
        return kbWriter;
    }

}
