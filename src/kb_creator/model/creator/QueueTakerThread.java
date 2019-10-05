package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.AbstractPair;

import java.util.concurrent.BlockingQueue;

public class QueueTakerThread implements Runnable {
    private BlockingQueue<AbstractPair> queue;
    private volatile boolean running = true;
    private int counter = 0;
    private AbstractPairBuffer l;
    private int currentK;

    public QueueTakerThread(BlockingQueue<AbstractPair> queue, AbstractPairBuffer l, int k) {
        this.queue = queue;
        this.l = l;
        this.currentK = k;
    }


    @Override
    public void run() {
        while (running) {
            try {
                l.addPair(queue.take());
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