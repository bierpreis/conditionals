package kb_creator.model.buffer;

import kb_creator.model.pairs.AbstractPair;

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

    protected AtomicInteger requestedListNumber;
    protected List<AbstractPair> requestedList;


    protected int lastIterationPairAmount;

    protected boolean deleteFiles;

    public AbstractPairBuffer(String baseFilePath) {
        status = BufferStatus.NOT_STARTED;
        this.tmpFilePath = baseFilePath + "/tmp/";
    }


    abstract public boolean hasMoreElements(int currentK);

    abstract public AbstractPair getNextPair(int currentK);

    abstract public boolean hasElementsForK(int requestedK);

    abstract public void prepareIteration(int requestedK);

    public abstract void finishIteration(int requestedK);

    abstract public void addNewList(List<AbstractPair> pairToAdd);

    abstract public void addPair(AbstractPair pairToAdd);

    public abstract void flushWritingElements();


    public BufferStatus getStatus() {
        return status;
    }

    public abstract void clear(int requestedK);

    public int getQueueToWriteSize() {
        return cpQueueToWrite.size();
    }

    public int getReaderBufferSize() {
        return 0;
    }


    public void stopThread() {
        running = false;
        status = BufferStatus.FINISHED;
    }


    public enum BufferStatus {
        WRITING, READING, NOT_STARTED, SLEEPING, FINISHING_ITERATION, PREPARING_NEXT_ITERATION, FINISHED
    }

    public int getLastIterationPairAmount() {
        return lastIterationPairAmount;
    }

    public void setDeletingFiles(boolean deleteFiles) {
        if (!(this instanceof DummyPairBuffer))
            System.out.println("set deleting buffer files: " + deleteFiles);
        this.deleteFiles = deleteFiles;
    }

}

