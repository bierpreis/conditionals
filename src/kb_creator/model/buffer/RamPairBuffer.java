package kb_creator.model.buffer;

import kb_creator.model.pairs.AbstractPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class RamPairBuffer extends AbstractPairBuffer {
    private int nextElementNumber;

    private List<List<AbstractPair>> candidatePairList;

    private Thread newIterationThread;
    private Thread lastIterationThread;


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
        System.out.println("preparing iteration: " + k);
        candidatePairList.add(Collections.synchronizedList(new ArrayList<>()));

        newIterationThread = new Thread(new NewIterationThread(newIterationQueue, candidatePairList, k));
        newIterationThread.setName("new iteration thread for k " + k);
        newIterationThread.start();

        lastIterationThread = new Thread(new LastIterationThread(lastIterationQueue, candidatePairList, k));
        lastIterationThread.setName("last iteration thread for k + k");
        lastIterationThread.start();

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

        //wait for and close new iteration thread
        while (!newIterationQueue.isEmpty())
            try {
                System.out.println("waiting for new iteration queue. items left : " + newIterationQueue.size());
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        newIterationThread.interrupt();

        //wait for and close last iterartion thread
        while (!lastIterationQueue.isEmpty())
            try {
                System.out.println("waiting for last iteration queue. items left : " + newIterationQueue.size());
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        lastIterationThread.interrupt();


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
        newIterationThread.interrupt();

        lastIterationThread.interrupt();
    }


    @Override
    public void setDeletingFiles(boolean deleteFiles) {
        //intentionally nothing
    }


    //getters for status info thread

    @Override
    public int getQueueToWriteSize() {
        return newIterationQueue.size();
    }


    @Override
    public int getReaderBufferSize() {
        return lastIterationQueue.size();
    }
}