package kb_creator.model.buffer;

import kb_creator.model.pairs.AbstractPair;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class AbstractPairBuffer {

    //it seems to be same performance if arrayblockingqueue or linkedblockingqueue
    protected BlockingQueue<AbstractPair> lastIterationQueue;
    protected BlockingQueue<AbstractPair> newIterationQueue;

    public AbstractPairBuffer(int maxNumberOfPairsInFile) {

        //value time 2 works
        //min value is maxNumberOfPairsInFile +1 else there will be a lock
        lastIterationQueue = new ArrayBlockingQueue<>(maxNumberOfPairsInFile * 2);
         newIterationQueue = new ArrayBlockingQueue<>(maxNumberOfPairsInFile * 2);
    }

    //variable

    protected int lastIterationPairAmount;


    //methods for iteration changes
    abstract public boolean hasMoreElementsForK(int k);

    abstract public boolean hasElementsForIteration(int k);

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

    public BlockingQueue<AbstractPair> getNewIterationQueue() {
        return newIterationQueue;
    }


}


