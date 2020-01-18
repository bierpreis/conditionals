package kb_creator.model.writer;


import kb_creator.model.logic.KnowledgeBase;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class AbstractKbWriter {

    protected volatile WriterStatus status;

    //arrayblockingqueue works a bit better than linkedblockingqueue
    //50k is a bit big. even 1 would work but slower
    protected BlockingQueue<KnowledgeBase> consistentWriterQueue = new ArrayBlockingQueue<>(50_000);
    protected BlockingQueue<KnowledgeBase> inconsistentWriterQueue = new ArrayBlockingQueue<>(50_000);

    protected Thread consistentThread;
    protected Thread inconsistentThread;

    //abstract methods

    public abstract void stopThreads();

    public abstract void finishAndStopThreads();

    public abstract void finishIteration();

    public abstract void prepareIteration(int k);

    //getters for counters

    public abstract long getTotalInconsistentCounter();

    public abstract long getTotalConsistentCounter();

    public abstract long getIterationConsistentCounter();

    public abstract long getIterationInconsistentCounter();


    //other getters

    public WriterStatus getStatus() {
        return status;
    }

    public BlockingQueue<KnowledgeBase> getConsistentQueue() {
        return consistentWriterQueue;
    }

    public BlockingQueue<KnowledgeBase> getInconsistentQueue() {
        return inconsistentWriterQueue;
    }
}
