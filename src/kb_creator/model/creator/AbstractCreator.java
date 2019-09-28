package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.knowledge_base.ObjectKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealListPair;
import kb_creator.model.propositional_logic.NewConditional;
import kb_creator.model.propositional_logic.signature.AbstractSignature;
import kb_creator.model.writer.AbstractKbWriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractCreator implements Runnable{



    protected void checkIfWaitForWriter() {
        if (waitForKbWriter)
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                waitForKbWriter = false;
            }

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

    private float calculateProgress(int pairCounter, int lastIterationAmount) {

        //avoid division with zero
        if (lastIterationAmount == 0) {
            return 0;

        }
        return (pairCounter / (float) lastIterationAmount) * 100;
    }

    public void waitForKbWriter() {
        creatorStatus = CreatorStatus.WAITING_FOR_WRITER;
        waitForKbWriter = true;
    }
}
