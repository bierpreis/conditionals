package kb_creator.model.buffer.ram;

import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealPair;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class LastIterationThread implements Runnable {
    private BlockingQueue<AbstractPair> lastIterationQueue;
    private List<List<AbstractPair>> candidatePairList;
    private int k;

    private volatile boolean hasMoreElements = true;

    public LastIterationThread(BlockingQueue<AbstractPair> lastIterationQueue, List<List<AbstractPair>> candidatePairList, int k) {
        this.lastIterationQueue = lastIterationQueue;
        this.candidatePairList = candidatePairList;
        this.k = k;
    }

    @Override
    public void run() {
        System.out.println("last iteration thread started for k " + k);
        for (AbstractPair pair : candidatePairList.get(k-1)) {
            try {
                //this should take compressed pairs from list and put real pairs in queue
                lastIterationQueue.put(new RealPair(pair));
            } catch (InterruptedException e) {
                //this should only be called by gui stop button
                return;
            }
        }
        hasMoreElements = false;
        System.out.println("last iteration thread finished for k " + k);
    }

    public boolean hasMoreElements() {
        return hasMoreElements;
    }
}
