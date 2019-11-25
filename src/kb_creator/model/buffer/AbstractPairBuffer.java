package kb_creator.model.buffer;

import kb_creator.model.propositional_logic.KnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.propositional_logic.PConditional;

import java.util.List;

public abstract class AbstractPairBuffer {

    //variables

    protected int lastIterationPairAmount;


    //methods for iteration changes
    abstract public boolean hasMoreElementsForK(int k);

    abstract public boolean hasElementsForIteration(int k);

    public abstract void prepareIteration(int requestedK);

    public abstract void finishIteration(int requestedK);

    protected abstract void deleteOldData(int requestedK);

    public abstract void stopLoop();

    public abstract void setDeletingFiles(boolean deleteFiles);

    abstract public void addNewList(List<AbstractPair> pairToAdd);


    //add pair methods

    public abstract void addPair(KnowledgeBase knowledgeBase, List<PConditional> candidatesToAdd);

    public abstract void addPair(AbstractPair pair);

    //get pair method

    abstract public AbstractPair getNextPair(int k);

    //other getters
    public abstract int getQueueToWriteSize();

    public abstract int getReaderBufferSize();


}


