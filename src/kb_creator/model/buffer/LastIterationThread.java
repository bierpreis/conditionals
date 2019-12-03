package kb_creator.model.buffer;

import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealPair;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class LastIterationThread implements Runnable {
    private BlockingQueue<AbstractPair> lastIterationQueue;
    private List<List<AbstractPair>> candidatePairList;
    private int k;

    public LastIterationThread(BlockingQueue<AbstractPair> lastIterationQueue, List<List<AbstractPair>> candidatePairList, int k) {
        this.lastIterationQueue = lastIterationQueue;
        this.candidatePairList = candidatePairList;
        this.k = k;
    }

    @Override
    public void run() {
        for (AbstractPair pair : candidatePairList.get(k)) {
            try {
                //this should take compressed pairs from list and put real pairs in queue
                lastIterationQueue.put(new RealPair(pair));
            } catch (InterruptedException e) {
                //todo: return? this should ONLY be called when stop is pressed.
                e.printStackTrace();
            }
        }
    }
}
