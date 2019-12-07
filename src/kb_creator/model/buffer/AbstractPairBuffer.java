package kb_creator.model.buffer;

import kb_creator.model.pairs.AbstractPair;

import java.util.concurrent.BlockingQueue;

public abstract class AbstractPairBuffer {

    protected BlockingQueue<AbstractPair> lastIterationQueue;
    protected BlockingQueue<AbstractPair> nextIterationQueue;

    protected Thread nextIterationThread;
    protected Thread lastIterationThread;

    //variable

    protected int lastIterationPairAmount;

    //loop methodsgit

    abstract public boolean hasMoreElementsForK(int k);

    abstract public boolean hasElementsForIteration(int k);

    //methods for iteration changes

    public abstract void prepareIteration(int requestedK);

    public abstract void finishIteration(int requestedK);

    protected abstract void deleteOldData(int requestedK);

    public abstract void stopLoop();

    //setting option

    public abstract void setDeletingFiles(boolean deleteFiles);

    //other getters
    public abstract int getQueueToWriteSize();

    public abstract int getReaderBufferSize();

    public BlockingQueue<AbstractPair> getLastIterationQueue() {
        return lastIterationQueue;
    }

    public BlockingQueue<AbstractPair> getNextIterationQueue() {
        return nextIterationQueue;
    }


}


