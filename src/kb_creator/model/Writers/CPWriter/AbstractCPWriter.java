package kb_creator.model.Writers.CPWriter;

import kb_creator.gui.writerPanel.candidatesPanel.CandidateStatus;
import kb_creator.model.Conditionals.Lists.AbstractCandidateList;
import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.Conditionals.Pairs.CandidateNumbersArrayPair;

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
