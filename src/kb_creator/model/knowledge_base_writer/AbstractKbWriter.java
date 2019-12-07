package kb_creator.model.knowledge_base_writer;


public abstract class AbstractKbWriter {

    protected volatile WriterStatus status;

    //abstract methods

    public abstract void stopThreads();

    public abstract void finishAndStopThreads();

    public abstract void finishIteration();

    public abstract void newIteration(int k);

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


}
