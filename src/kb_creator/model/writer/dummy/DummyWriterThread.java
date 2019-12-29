package kb_creator.model.writer.dummy;

import kb_creator.model.logic.KnowledgeBase;

import java.util.concurrent.BlockingQueue;

public class DummyWriterThread implements Runnable {
    private volatile boolean running = true;
    private BlockingQueue<KnowledgeBase> queue;

    private long iterationCounter;
    private long totalCounter;

    public DummyWriterThread(BlockingQueue<KnowledgeBase> queue) {
        this.queue = queue;

    }

    @Override
    public void run() {
        while (running) {
            //this should delete all entries and wait if there are no entries
            try {
                queue.take();
                iterationCounter++;
                totalCounter++;
            } catch (InterruptedException e) {
                running = false;
                System.out.println("dummy writer interrupted");
                break;
            }
        }
        System.out.println("dummy kbWriter thread finished");
    }

    //this should only be called by gui
    public void stopLoop() {
        while (!queue.isEmpty()) {
            try {
                System.out.println("dummy writer sleeping");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        running = false;
    }

    public void newIteration() {
        iterationCounter = 0;
    }

    //getter

    public long getTotalCounter() {
        return totalCounter;
    }

    public long getIterationCounter() {
        return iterationCounter;
    }

}
