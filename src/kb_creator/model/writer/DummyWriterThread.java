package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

import java.util.concurrent.BlockingQueue;

public class DummyWriterThread implements Runnable {
    private volatile boolean running = true;
    private BlockingQueue<AbstractKnowledgeBase> queue;
    private boolean bool; //todo: delete when not needed anymore
    private int counter;

    public DummyWriterThread(BlockingQueue<AbstractKnowledgeBase> queue) {
        this.queue = queue;

    }

    public DummyWriterThread(BlockingQueue<AbstractKnowledgeBase> queue, boolean bool) {
        this.queue = queue;
        this.bool = bool;
    }

    @Override
    public void run() {
        while (running) {
            //this should delete all entries and wait if there are no entries
            try {
                queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("STOPPED"); //todo: this can not be seen!?
    }

    public void stop() {
        System.out.println("trying to stop dummy writer");
        while (!queue.isEmpty()) {
            try {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!dummy writer deleting queue until empty");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        running = false;
        if (bool)
            System.out.println(counter); //todo: this counter doesnt fit with gui counter?!
    }
}
