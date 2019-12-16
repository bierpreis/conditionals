package kb_creator.model.writer;


import kb_creator.model.logic.KnowledgeBase;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class AbstractKbWriter {

    protected volatile WriterStatus status;

    //arrayblockingqueue works a bit better than linkedblockingqueue
    //todo. queue should be set bigger then filesize?
    protected BlockingQueue<KnowledgeBase> consistentWriterQueue = new ArrayBlockingQueue<>(5000);
    protected BlockingQueue<KnowledgeBase> inconsistentWriterQueue = new ArrayBlockingQueue<>(5000);

    protected Thread consistentThread;
    protected Thread inconsistentThread;

    //abstract methods

    public abstract void stopThreads();

    public abstract void finishAndStopThreads();

    public abstract void finishIteration();

    public abstract void prepareIteration(int k);

    //getters for counters

    public abstract int getTotalInconsistentCounter();

    public abstract int getTotalConsistentCounter();

    public abstract int getIterationConsistentCounter();

    public abstract int getIterationInconsistentCounter();


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
