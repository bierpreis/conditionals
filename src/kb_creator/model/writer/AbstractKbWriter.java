package kb_creator.model.writer;


public abstract class AbstractKbWriter {

    protected boolean running = true;

    protected volatile WriterStatus status;


    //getters for counters
    public abstract int getTotalInconsistentCounter();

    public abstract int getTotalConsistentCounter();

    public abstract int getIterationConsistentCounter();

    public abstract int getIterationInconsistentCounter();


    //getters for queue
    public abstract int getConsistentQueue();

    public abstract int getInconsistentQueue();


    public WriterStatus getStatus() {
        return status;
    }

    public abstract void stopThreads();

    public abstract void finishIteration();

    public abstract void newIteration();


}
