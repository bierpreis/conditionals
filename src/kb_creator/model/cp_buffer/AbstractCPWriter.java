package kb_creator.model.cp_buffer;

import kb_creator.model.conditionals.pairs.AbstractPair;

import java.util.List;
import java.util.Queue;

public abstract class AbstractCPWriter implements Runnable {
    protected Queue<AbstractPair> cpQueueToWrite;
    protected volatile CandidateStatus status;

    protected volatile int writeCounter;
    protected volatile int readCounter;

    @Override
    public abstract void run();


    public abstract int getQueueToWriteSize();


    public abstract void addCpToWrite(AbstractPair pair);

    public CandidateStatus getStatus() {
        return status;
    }

    public abstract List<AbstractPair> getList(int requestedK);

    public abstract List<AbstractPair> readList(int requestedK);

    public abstract void addCpList(List<AbstractPair> listToadd);

    public abstract void flush();

    public int getReaderCounter() {
        return readCounter;
    }


}
