package kb_creator.model.writer;


public abstract class AbstractKbWriter {

    protected WriterStatus status;

    public abstract int getInconsistentCounter();

    public abstract int getConsistentCounter();

    public abstract int getConsistentQueue();

    public abstract int getInconsistentQueue();

    public WriterStatus getStatus() {
        return status;
    }

    public abstract void stop();

}
