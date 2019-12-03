package kb_creator.model.buffer;

import kb_creator.model.pairs.AbstractPair;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public abstract class AbstractPairBuffer{

    protected BlockingQueue<AbstractPair> lastIterationQueue;
    protected BlockingQueue<AbstractPair> newIterationQueue;

    public AbstractPairBuffer(BlockingQueue<AbstractPair> newIterationQueue, BlockingQueue<AbstractPair> lastIterationQueue) {
        this.newIterationQueue = newIterationQueue;
        this.lastIterationQueue = lastIterationQueue;
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

    //other getters
    public abstract int getQueueToWriteSize();

    public abstract int getReaderBufferSize();

    public BlockingQueue<AbstractPair> getLastIterationQueue(){
        return lastIterationQueue;
    }

    public BlockingQueue<AbstractPair> getNewIterationQueue(){
        return newIterationQueue;
    }



}


