package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;

import java.io.File;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractCandidateCollection implements Runnable {


    protected volatile boolean running;
    protected volatile Queue<AbstractPair> cpQueueToWrite;

    protected volatile BufferStatus status;

    protected volatile int writingFileNameCounter;
    protected volatile int readingFileNameCounter;

    protected volatile int pairReaderCounter;

    protected final int maxNumberOfPairsInFile = 200;

    protected String tmpFilePath;
    protected volatile boolean flushRequested;

    protected AtomicInteger requestedListNumber;
    protected List<AbstractPair> requestedList;
    protected volatile boolean requestedListIsReady;

    public AbstractCandidateCollection(String tmpFilePath) {
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

    public int getReaderCounter() {
        return pairReaderCounter;
    }

    public abstract void clear(int requestedK);

    public int getQueueToWriteSize() {
        return cpQueueToWrite.size();
    }


    public void deleteFiles(int numberOfConditionals) {
        System.out.println("trying to delete " + numberOfConditionals + " element pairs");
        File fileToDelete = new File(tmpFilePath + "/" + numberOfConditionals + "/");
        boolean fileDeletedSuccesfully;
        try {
            for (File file : fileToDelete.listFiles()) {
                if (!file.isDirectory()) {
                    if (!
                            file.delete()
                    )
                        System.out.println("deleting candidate pair file failed!");

                }

            }
        } catch (NullPointerException e) {
            System.out.println("no " + numberOfConditionals + " element pairs found for deleting");
        }
    }

    public void stopThread() {
        running = false;
        status = BufferStatus.FINISHED;
    }


    public enum BufferStatus {
        WRITING, READING, NOT_STARTED, SLEEPING, FINISHING_ITERATION, PREPARING_NEXT_ITERATION, FINISHED;
    }

}


