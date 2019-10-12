package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.AbstractPair;

import java.util.concurrent.BlockingQueue;

public class InputQueueThread implements Runnable {
    private BlockingQueue<AbstractPair> queue;
    private AbstractPairBuffer l;
    private volatile int counter = 0;
    private volatile int currentK;

    public InputQueueThread(BlockingQueue<AbstractPair> queue, AbstractPairBuffer l, int currentK) {
        this.queue = queue;
        this.l = l;
        this.currentK = currentK;
    }

    @Override
    public void run() {
        //like this the thread will close when the work is finished
        while (l.hasMoreElementsForK(currentK)) {
            try {
                queue.put(l.getNextPair(currentK));  //todo: this caused null pointer exception at k = 7?!# l.getNextpair could return null if it gets interrupted while waiting. what to do?
                //happened again!
                counter++;
            } catch (InterruptedException e) {
                //this is triggered by gui stop button and ends this thread
                return;
            }
        }
    }

    public int getCounter() {
        return counter;
    }
}
