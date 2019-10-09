package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.AbstractPair;

import java.util.concurrent.BlockingQueue;

public class OutputQueueThread implements Runnable {
    private BlockingQueue<AbstractPair> queue;
    private volatile boolean running = true;
    private volatile int counter = 0;
    private AbstractPairBuffer l;

    public OutputQueueThread(BlockingQueue<AbstractPair> queue, AbstractPairBuffer l) {
        this.queue = queue;
        this.l = l;
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

    public void stopLoop() {
        while (!queue.isEmpty())
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        running = false;
        System.out.println("Output queue Thread stopped. Queue: " + queue.size());
    }

    public int getCounter() {
        return counter;
    }
}
