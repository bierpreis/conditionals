package kb_creator.model.buffer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.propositional_logic.NewConditional;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractPairBuffer implements Runnable {

    protected boolean running = true;

    protected BufferStatus status;

    protected int pairReaderCounter;

    protected int maxNumberOfPairsInFile;

    protected String tmpFilePath;

    protected int lastIterationPairAmount;

    protected boolean deleteFiles;

    //k will be set by prepare iteration methods

    public AbstractPairBuffer(String baseFilePath) {
        status = BufferStatus.NOT_STARTED;
        this.tmpFilePath = baseFilePath + "/tmp/";
    }

    abstract public boolean hasMoreElements(int k);

    abstract public AbstractPair getNextPair(int k);

    abstract public boolean hasElementsForNextK(int k);


    public abstract void prepareIteration(int requestedK);

    public abstract void finishIteration(int requestedK);


    abstract public void addNewList(List<AbstractPair> pairToAdd);

    public abstract void addPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidatesToAdd);

    public abstract void addPair(AbstractPair pair);

    public BufferStatus getStatus() {
        return status;
    }

    protected abstract void clear(int requestedK);

    public int getQueueToWriteSize() {
        return 0;
    } //todo: wtf is this

    public abstract int getReaderBufferSize();


    public void setFinished() {
        running = false;
        status = BufferStatus.FINISHED;
    }


    public enum BufferStatus {
        WRITING, READING, NOT_STARTED, SLEEPING, FINISHING_ITERATION, PREPARING_NEXT_ITERATION, FINISHED
    }

    public void setDeletingFiles(boolean deleteFiles) {
        if (!(this instanceof DummyPairBuffer))
            System.out.println("set deleting buffer files: " + deleteFiles);
        this.deleteFiles = deleteFiles;
    }

    abstract public boolean checkIfShouldWrite();

    abstract public boolean checkIfShouldRead();

    public abstract void notifyBuffer();

}


