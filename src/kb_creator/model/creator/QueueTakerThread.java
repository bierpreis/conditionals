package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.AbstractPair;

import java.util.concurrent.BlockingQueue;

public class QueueTakerThread implements Runnable {
    private BlockingQueue<AbstractPair> queue;
    private volatile boolean running = true;
    private AbstractPairBuffer l;

    public QueueTakerThread(BlockingQueue<AbstractPair> queue, AbstractPairBuffer l) {
        this.queue = queue;
        this.l = l;
    }


    @Override
    public void run() {
        while (running) {
            try {
                l.addPair(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        running = false;
    }
}
