package kb_creator.model.writer;


public abstract class AbstractKbWriter {

    protected boolean running = true;

    protected volatile WriterStatus status;

    public abstract int getInconsistentCounter();

    public abstract int getConsistentCounter();

    public abstract int getConsistentQueue();

    public abstract int getInconsistentQueue();

    public WriterStatus getStatus() {
        return status;
    }

    public abstract void stop();

}
