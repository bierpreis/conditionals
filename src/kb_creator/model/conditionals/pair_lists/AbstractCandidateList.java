package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.cp_buffer.AbstractCPWriter;

import java.util.Collection;
import java.util.List;

public abstract class AbstractCandidateList {
    protected List<List<AbstractPair>> candidatePairList;
    protected AbstractCPWriter cpFileBuffer;

    abstract public Collection<AbstractPair> getListForK(int requestedK);

    abstract public Collection<AbstractPair> readListForK(int requestedK);

    abstract public void addNewList(int k, List<AbstractPair> pairToAdd);

    abstract public void addPair(AbstractPair pairToAdd);

    public AbstractCPWriter getCpWriter() {
        return cpFileBuffer;
    }
}
