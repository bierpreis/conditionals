package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.AbstractPair;

import java.util.concurrent.BlockingQueue;

public class QueuePutterThread implements Runnable {
    private BlockingQueue<AbstractPair> queue;
    private volatile boolean running = true;
    private AbstractPairBuffer l;

    public QueuePutterThread(BlockingQueue<AbstractPair> queueu, AbstractPairBuffer l) {
        this.queue = queueu;
        this.l = l;
    }

    //todo: change to take
    @Override
    public void run() {
        while (running) {
            try {
                queue.put(l.getNextPair(0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void stop() {
        running = false;
    }
}
