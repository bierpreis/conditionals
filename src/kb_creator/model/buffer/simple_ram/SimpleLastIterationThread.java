package kb_creator.model.buffer.simple_ram;

import kb_creator.model.buffer.ram.LastIterationThread;
import kb_creator.model.pairs.AbstractPair;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class SimpleLastIterationThread extends LastIterationThread {

    public SimpleLastIterationThread(BlockingQueue<AbstractPair> lastIterationQueue, List<List<AbstractPair>> candidatePairList, int k) {
        super(lastIterationQueue, candidatePairList, k);
    }

    @Override
    public void run() {
        System.out.println("last iteration thread started for k " + k);
        for (AbstractPair pair : candidatePairList.get(k-1)) {
            try {
                //this should take compressed pairs from list and put real pairs in queue
                lastIterationQueue.put(pair);
            } catch (InterruptedException e) {
                //this should only be called by gui stop button
                return;
            }
        }
        hasMoreElements = false;
        System.out.println("last iteration thread finished for k " + k);
    }
}
