package kb_creator.model.Conditionals.Lists;

import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.Conditionals.Pairs.CandidateNumbersListPair;
import kb_creator.model.Writers.CPWriter.AbstractCPWriter;
import kb_creator.model.Writers.CPWriter.CpFileBuffer;

import java.util.List;

public abstract class AbstractCandidateList {
    protected List<List<AbstractPair>> candidatePairList;
    protected AbstractCPWriter cpFileBuffer;

    abstract public List<AbstractPair> getListForK(int requestedK);

    abstract public void addNewList(int k, List<AbstractPair> pairToAdd);

    abstract public void addPair(AbstractPair pairToAdd);

    public AbstractCPWriter getCpWriter() {
        return cpFileBuffer;
    }
}
