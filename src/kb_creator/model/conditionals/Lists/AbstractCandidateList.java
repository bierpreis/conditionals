package kb_creator.model.conditionals.Lists;

import kb_creator.model.conditionals.Pairs.AbstractPair;
import kb_creator.model.cp_buffer.AbstractCPWriter;

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
