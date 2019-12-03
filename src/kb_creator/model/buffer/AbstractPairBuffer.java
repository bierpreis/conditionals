package kb_creator.model.buffer;

import kb_creator.model.pairs.AbstractPair;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class AbstractPairBuffer {


    //todo: this size depends on file size. must be bigger than file size???!
    //it seems to be same performance if arrayblockingqueue or linkedblockingqueue
    protected BlockingQueue<AbstractPair> lastIterationQueue = new ArrayBlockingQueue<>(80000);
    protected BlockingQueue<AbstractPair> newIterationQueue = new ArrayBlockingQueue<>(80000);
    ;


    public AbstractPairBuffer() {

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

    public BlockingQueue<AbstractPair> getLastIterationQueue() {
        return lastIterationQueue;
    }

    public BlockingQueue<AbstractPair> getNewIterationQueue() {
        return newIterationQueue;
    }


}


