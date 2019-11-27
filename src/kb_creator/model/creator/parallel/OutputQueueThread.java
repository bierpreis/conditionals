package kb_creator.model.creator.parallel;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.AbstractPair;

import java.util.concurrent.BlockingQueue;

//todo: this needs to change to pair collector?!
public class OutputQueueThread implements Runnable {
    private BlockingQueue<AbstractPair> queue;
    private AbstractPairBuffer l;

    private volatile boolean running = true;

    public OutputQueueThread(BlockingQueue<AbstractPair> queue, AbstractPairBuffer l) {
        this.queue = queue;
        this.l = l;
    }


    @Override
    public void run() {
        while (running) {
            try {
                l.addPair(queue.take());
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void finishQueueAndStop() {
        while (!queue.isEmpty())
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        running = false;
    }
}
