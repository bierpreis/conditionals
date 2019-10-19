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
                //queue would throw null pointer exception when the element is null. but this never happened so its just ok.
                queue.put(l.getNextPair(currentK)); //todo: Exception in thread "InputQueueThread" java.lang.NullPointerException with buffering and not deleting files?!
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
