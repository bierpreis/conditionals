package kb_creator.model.writer.dummy;

import kb_creator.model.propositional_logic.KnowledgeBase;

import java.util.concurrent.BlockingQueue;

public class DummyWriterThread implements Runnable {
    private volatile boolean running = true;
    private BlockingQueue<KnowledgeBase> queue;

    private int iterationCounter;
    private int totalCounter;

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
                return; //can be triggered by stop button
            }
        }
        System.out.println("dummy kbWriter thread finished");
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

    public void newIteration() {
        iterationCounter = 0;
    }

    //getter

    public int getTotalCounter() {
        return totalCounter;
    }

    public int getIterationCounter() {
        return iterationCounter;
    }
}
