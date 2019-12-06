package kb_creator.model.buffer;

import kb_creator.model.pairs.AbstractPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RamPairBuffer extends AbstractPairBuffer {

    private List<List<AbstractPair>> candidatePairList;

    private Thread newIterationThread;
    private Thread lastIterationThread;

    private LastIterationThread lastIterationThreadObject;


    public RamPairBuffer() {

        // 1000 is more than enough
        lastIterationQueue = new ArrayBlockingQueue<>(1000);
        newIterationQueue = new ArrayBlockingQueue<>(1000);

        candidatePairList = Collections.synchronizedList(new ArrayList<>());
    }


    //iteration change methods

    @Override
    public boolean hasMoreElementsForK(int k) {
        if (!lastIterationQueue.isEmpty())
            return true;
        else return lastIterationThreadObject.hasMoreElements();
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

        //don't start this for k = 0
        //there is no last iteration for 0
        if (k != 0) {

            lastIterationThread = new Thread(lastIterationThreadObject = new LastIterationThread(lastIterationQueue, candidatePairList, k));
            lastIterationThread.setName("last iteration thread for k + k");
            lastIterationThread.start();
        }

        System.out.println("finished preparing iteration: " + k);
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

        //wait for and close last iteration thread
        while (!lastIterationQueue.isEmpty())
            try {
                System.out.println("waiting for last iteration queue. items left : " + newIterationQueue.size());
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        //avoid null pointer because at iteration 0 there is no last iteration
        if (lastIterationThread != null)
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