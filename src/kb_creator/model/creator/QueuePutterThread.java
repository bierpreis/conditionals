package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.AbstractPair;

import java.util.concurrent.BlockingQueue;

public class QueuePutterThread implements Runnable {
    private BlockingQueue<AbstractPair> queue;
    private volatile boolean running = true;
    private AbstractPairBuffer l;
    private int counter = 0;
    private int currentK;

    public QueuePutterThread(BlockingQueue<AbstractPair> queue, AbstractPairBuffer l, int currentK) {
        this.queue = queue;
        this.l = l;
        this.currentK = currentK;
    }

    //todo: change to take
    @Override
    public void run() {
        while (running) {
            try {
                queue.put(l.getNextPair(currentK));
                counter++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void stop() {
        running = false;
    }

    public int getCounter() {
        return counter;
    }
}
