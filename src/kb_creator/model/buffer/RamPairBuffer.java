package kb_creator.model.buffer;

import kb_creator.model.pairs.RealPair;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.CompressedPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class RamPairBuffer extends AbstractPairBuffer {
    private int nextElementNumber;
    private List<List<CompressedPair>> candidatePairList;

    private Thread bufferThread;


    //todo: inner thread for this. the outer can then start it just like the hdd buffer.
    public RamPairBuffer(BlockingQueue<RealPair> pairsQueue) {
        super(pairsQueue);
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
        bufferThread = new Thread(new RamBufferThread(inputQueue, candidatePairList, k));
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
        while (!inputQueue.isEmpty())
            try {
                System.out.println("sleeping. queue: " + inputQueue.size());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        //todo: buffer thread is not closed correctly?!
        bufferThread.interrupt();


        lastIterationPairAmount = candidatePairList.get(requestedK).size();
        deleteOldData(requestedK - 1);
    }

    //this is only called by gui stop button
    @Override
    public void stopLoop() {
        while (!inputQueue.isEmpty())
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        bufferThread.interrupt();
    }

    @Override
    public void setDeletingFiles(boolean deleteFiles) {
        //intentionally nothing
    }


    // add pair methods

    //todo: type?!
    @Override
    public void addList(List listToAdd) {
        candidatePairList.add(Collections.synchronizedList(listToAdd));


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
