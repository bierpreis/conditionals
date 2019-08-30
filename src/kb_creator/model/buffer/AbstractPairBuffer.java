package kb_creator.model.buffer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.propositional_logic.NewConditional;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractPairBuffer implements Runnable {


    protected boolean running;
    protected Queue<AbstractPair> cpQueueToWrite;

    protected BufferStatus status;

    protected int writingFileNameCounter;
    protected int readingFileNameCounter;

    protected int pairReaderCounter;

    protected int maxNumberOfPairsInFile;

    protected String tmpFilePath;
    protected volatile boolean flushRequested;

    protected int lastIterationPairAmount;

    protected boolean deleteFiles;

    public AbstractPairBuffer(String baseFilePath) {
        status = BufferStatus.NOT_STARTED;
        this.tmpFilePath = baseFilePath + "/tmp/";
    }


    abstract public boolean hasMoreElements(int currentK);

    abstract public AbstractPair getNextPair(int currentK);

    abstract public boolean hasElementsForK(int requestedK);

    public abstract void prepareIteration(int requestedK);

    public abstract void finishIteration(int requestedK);

    abstract public void addNewList(List<AbstractPair> pairToAdd);

    public abstract void addPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidatesToAdd);


    public BufferStatus getStatus() {
        return status;
    }

    protected abstract void clear(int requestedK);

    public int getQueueToWriteSize() {
        return cpQueueToWrite.size();
    }

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


}


