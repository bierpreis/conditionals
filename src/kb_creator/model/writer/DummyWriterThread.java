package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

import java.util.concurrent.BlockingQueue;

public class DummyWriterThread implements Runnable {
    private volatile boolean running = true;
    private BlockingQueue<AbstractKnowledgeBase> queue;

    private int iterationCounter;
    private int totalCounter;

    public DummyWriterThread(BlockingQueue<AbstractKnowledgeBase> queue) {
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
                //intentionally nothing
            }
        }
        System.out.println("dummy writer thread finished");
    }


    public void stopLoop() {
        while (!queue.isEmpty()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        running = false;
    }

    public int getTotalCounter() {
        return totalCounter;
    }

    //todo. use this
    public int getIterationCounter() {
        return iterationCounter;
    }

    public void newIteration() {
        iterationCounter = 0;
    }
}
