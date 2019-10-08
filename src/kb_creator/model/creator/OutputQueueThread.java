package kb_creator.model.creator;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.AbstractPair;

import java.util.concurrent.BlockingQueue;

public class OutputQueueThread implements Runnable {
    private BlockingQueue<AbstractPair> queue;
    private volatile boolean running = true;
    private int counter = 0;
    private AbstractPairBuffer l;
    private int currentK;

    public OutputQueueThread(BlockingQueue<AbstractPair> queue, AbstractPairBuffer l, int k) {
        System.out.println("new output queue thread for k: " + k);
        this.queue = queue;
        this.l = l;
        this.currentK = k;
    }


    @Override
    public void run() {
        System.out.println("started output queue thread for k= " + currentK);
        while (running) {
            try {
                l.addPair(queue.take());
                counter++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("finished output queue thread for k= " + currentK);
    }

    public void stopWhenFinished() {
        System.out.println("tying to finish output queue");
        while (!queue.isEmpty())
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println("finished output queue thread");
        running = false;
    }

    public int getCounter() {
        return counter;
    }
}
