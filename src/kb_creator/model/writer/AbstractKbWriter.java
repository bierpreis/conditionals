package kb_creator.model.writer;


public abstract class AbstractKbWriter {

    protected int iterationConsistentKbCounter = 0;

    protected boolean running = true;

    protected volatile WriterStatus status;

    public abstract int getInconsistentCounter();

    public abstract int getConsistentCounter();

    public abstract int getConsistentQueue();

    public abstract int getInconsistentQueue();

    public WriterStatus getStatus() {
        return status;
    }

    public abstract void stopThreads();

    public int getIterationConsistentKbCounter() {
        return iterationConsistentKbCounter;
    }

    public void newIteration(){
        iterationConsistentKbCounter = 0;
    }



}
