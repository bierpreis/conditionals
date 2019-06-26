package kb_creator.model.cp_buffer;

import kb_creator.model.conditionals.pairs.AbstractPair;

import java.util.Collection;
import java.util.List;
import java.util.Queue;

public abstract class AbstractBuffer implements Runnable {
    protected volatile Queue<AbstractPair> cpQueueToWrite;
    protected volatile BufferStatus status;

    protected volatile int writeCounter;
    protected volatile int readCounter;

    @Override
    public abstract void run();


    public abstract int getQueueToWriteSize();


    public abstract void addCpToWrite(AbstractPair pair);

    public BufferStatus getStatus() {
        return status;
    }

    public abstract Collection<AbstractPair> getList(int requestedK);

    public abstract void prepareCollection(int requestedK);

    public abstract void addCpList(List<AbstractPair> listToadd);

    public abstract void flushWritingElements();

    public int getReaderCounter() {
        return readCounter;
    }



    public enum BufferStatus {
        WRITING, READING, NOT_STARTED, SLEEPING;
    }

}


