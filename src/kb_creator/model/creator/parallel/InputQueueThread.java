package kb_creator.model.creator.parallel;

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
                queue.put(l.getNextPair(currentK));
                counter++;
            } catch (InterruptedException e) {
                //this is triggered by gui stop button and ends this thread
                return;
            } catch (NullPointerException np) {
                //if the element to put is null, a null pointer exception is thrown. that should not be too bad.
                System.out.println("Thread received null element.");
            }
        }
    }

    public int getCounter() {
        return (counter - queue.size());
    }
}
