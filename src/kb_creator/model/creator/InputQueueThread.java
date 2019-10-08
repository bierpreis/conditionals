package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.AbstractPair;

import java.util.concurrent.BlockingQueue;

public class InputQueueThread implements Runnable {
    private BlockingQueue<AbstractPair> queue;
    private AbstractPairBuffer l;
    private volatile int counter = 0;
    private int currentK;

    public InputQueueThread(BlockingQueue<AbstractPair> queue, AbstractPairBuffer l, int currentK) {
        this.queue = queue;
        this.l = l;
        this.currentK = currentK;
    }

    @Override
    public void run() {
        System.out.println("started input queue thread for k= " + currentK);

        //like this the thread will close when the work is finished
        while (l.hasMoreElementsForK(currentK)) {
            try {
                queue.put(l.getNextPair(currentK));  //todo: this caused null pointer exception at k = 7?!
                counter++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("finished input queue thread for k= " + currentK);
    }

    public int getCounter() {
        return counter;
    }
}
