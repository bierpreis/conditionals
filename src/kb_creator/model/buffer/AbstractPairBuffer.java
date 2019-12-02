package kb_creator.model.buffer;

import kb_creator.model.pairs.RealPair;
import kb_creator.model.propositional_logic.KnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.propositional_logic.PConditional;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public abstract class AbstractPairBuffer implements Runnable{
    protected BlockingQueue<RealPair> inputQueue;

    public AbstractPairBuffer(BlockingQueue<RealPair> inputQueue) {
        this.inputQueue = inputQueue;
    }

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


    //get pair method

    abstract public AbstractPair getNextPair(int k);

    //other getters
    public abstract int getQueueToWriteSize();

    public abstract int getReaderBufferSize();


}


