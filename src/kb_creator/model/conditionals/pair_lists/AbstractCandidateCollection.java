package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.cp_buffer.AbstractCPWriter;

import java.util.List;

public abstract class AbstractCandidateCollection {
    protected List<List<AbstractPair>> candidatePairList;
    protected AbstractCPWriter cpFileBuffer;

    abstract public boolean hasNext();

    abstract public AbstractPair getNextElement();

    abstract public boolean hasElementsForK(int requestedK);

    abstract public void prepareCollection(int requestedK);

    abstract public void addNewList(int k, List<AbstractPair> pairToAdd);

    abstract public void addPair(AbstractPair pairToAdd);

    public AbstractCPWriter getCpWriter() {
        return cpFileBuffer;
    }
}
