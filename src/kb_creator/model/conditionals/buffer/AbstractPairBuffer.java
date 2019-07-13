package kb_creator.model.conditionals.buffer;

import kb_creator.model.conditionals.pairs.AbstractPair;

import java.io.File;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractPairBuffer implements Runnable {


    protected volatile boolean running;
    protected volatile Queue<AbstractPair> cpQueueToWrite;

    protected volatile BufferStatus status;

    protected volatile int writingFileNameCounter;
    protected volatile int readingFileNameCounter;

    protected volatile int pairReaderCounter;

    protected int maxNumberOfPairsInFile;

    protected String tmpFilePath;
    protected volatile boolean flushRequested;

    protected AtomicInteger requestedListNumber;
    protected List<AbstractPair> requestedList;
    protected volatile boolean requestedListIsReady;
    protected volatile int lastIterationPairAmount;

    protected boolean deleteFiles;

    public AbstractPairBuffer(String tmpFilePath) {
        status = BufferStatus.NOT_STARTED;
        this.tmpFilePath = tmpFilePath + "/tmp/";
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


    public void stopThread() {
        running = false;
        status = BufferStatus.FINISHED;
    }


    public enum BufferStatus {
        WRITING, READING, NOT_STARTED, SLEEPING, FINISHING_ITERATION, PREPARING_NEXT_ITERATION, FINISHED;
    }

    public int getLastIterationPairAmount() {
        return lastIterationPairAmount;
    }

    public void setDeletingFiles(boolean deleteFiles) {
        System.out.println("deleting buffer files: " + deleteFiles);
        this.deleteFiles = deleteFiles;
    }

}


