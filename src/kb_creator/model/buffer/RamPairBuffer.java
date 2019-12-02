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
    private int k;

    private Thread bufferThread;

    private volatile boolean running;

    public RamPairBuffer(BlockingQueue<RealPair> pairsQueue) {
        super(pairsQueue);
        candidatePairList = Collections.synchronizedList(new ArrayList<>());
        running = true;
        System.out.println("!!!new buffer object");
    }

    @Override
    public void run() {
        System.out.println("!!!buffer thread started!");
        while (running) {
            try {
                candidatePairList.get(k).add(new CompressedPair(inputQueue.take()));
            } catch (InterruptedException e) {
                System.out.println("!!interrupted"); //todo. maybe close here?
                //running = false;
                //this is triggered when iteration is finished and thread stops
            }
        }
        System.out.println("!!!buffer thread closed!");
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
        nextElementNumber = 0;
        this.k = k;

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
        running = false;
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
        running = false;
        bufferThread.interrupt();
    }

    @Override
    public void setDeletingFiles(boolean deleteFiles) {
        //intentionally nothing
    }


    // add pair methods

    //todo: type?! AND starting thread like this is shit!
    @Override
    public void addListAndStartThread(List listToAdd) {
        running = true;
        candidatePairList.add(Collections.synchronizedList(listToAdd));

        bufferThread = new Thread(this);
        bufferThread.setName("buffer");
        bufferThread.start();

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
