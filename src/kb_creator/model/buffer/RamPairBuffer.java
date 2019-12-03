package kb_creator.model.buffer;

import kb_creator.model.pairs.AbstractPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class RamPairBuffer extends AbstractPairBuffer {
    private int nextElementNumber;
    private List<List<AbstractPair>> candidatePairList;

    private Thread bufferThread;


    public RamPairBuffer(BlockingQueue<AbstractPair> newIterationQueue, BlockingQueue<AbstractPair> lastIterationQueue) {
        super(newIterationQueue, lastIterationQueue); //todo. implement lastIterationQueue
        candidatePairList = Collections.synchronizedList(new ArrayList<>());
    }


    //iteration change methods

    @Override
    public boolean hasMoreElementsForK(int k) {
        return (nextElementNumber) < (candidatePairList.get(k - 1).size());
    }

    @Override
    public boolean hasElementsForIteration(int k) {
        return !candidatePairList.get(k - 1).isEmpty();
    }

    @Override
    public void prepareIteration(int k) {
        candidatePairList.add(Collections.synchronizedList(new ArrayList<>()));
        bufferThread = new Thread(new RamBufferThread(newIterationQueue, candidatePairList, k));
        bufferThread.setName("buffer for k " + k);
        bufferThread.start();
        System.out.println("preparing iteration: " + k);
        nextElementNumber = 0;
    }

    @Override
    protected void deleteOldData(int requestedK) {
        //System.out.println("clearing list for k " + requestedK);

        //don't clear list(-1) it wont work :D
        if (requestedK > 0)
            candidatePairList.get(requestedK).clear();
    }


    @Override
    public void finishIteration(int requestedK) {
        System.out.println("finishing iteration: " + requestedK);
        while (!newIterationQueue.isEmpty())
            try {
                System.out.println("sleeping. queue: " + newIterationQueue.size());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        bufferThread.interrupt();


        lastIterationPairAmount = candidatePairList.get(requestedK).size();
        deleteOldData(requestedK - 1);
    }

    //this is only called by gui stop button
    @Override
    public void stopLoop() {
        while (!newIterationQueue.isEmpty())
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        bufferThread.interrupt();
    }

    @Override
    public void addList(List<AbstractPair> listToAdd) {
        candidatePairList.get(candidatePairList.size() - 1).addAll(listToAdd);


    }

    @Override
    public void setDeletingFiles(boolean deleteFiles) {
        //intentionally nothing
    }


    //getters

    @Override
    public AbstractPair getNextPair(int k) {
        nextElementNumber++;
        return candidatePairList.get(k - 1).get(nextElementNumber - 1);
    }

    @Override
    public int getQueueToWriteSize() {
        return 0;
    }


    @Override
    public int getReaderBufferSize() {
        return 0;
    }
}