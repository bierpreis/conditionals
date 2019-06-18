package kb_creator.model.CpBuffer;

import kb_creator.model.Conditionals.Pairs.AbstractPair;

import java.util.List;
import java.util.Queue;

public abstract class AbstractCPWriter implements Runnable {
    protected Queue<AbstractPair> cpQueueToWrite;
    protected CandidateStatus status;

    @Override
    public abstract void run();


    public abstract int getQueueToWriteSize();


    public abstract void addCpToWrite(AbstractPair pair);

    public CandidateStatus getStatus() {
        return status;
    }

    public abstract List<AbstractPair> getList(int requestedK);

    public abstract void addCpList(List<AbstractPair> listToadd);

}
