package kb_creator.model.writer;

import java.util.concurrent.BlockingQueue;

public class DummyWriterThread implements Runnable {
    private volatile boolean running = true;
    private BlockingQueue queue;
    private boolean bool; //todo: delete when not needed anymore
    private int counter;

    public DummyWriterThread(BlockingQueue queue) {
        this.queue = queue;

    }

    public DummyWriterThread(BlockingQueue queue, boolean bool) {
        this.queue = queue;
        this.bool = bool;
    }

    @Override
    public void run() {
        while (running) {
            //this should delete all entries and wait if there are no entries
            try {
                queue.take(); //todo: this is shit!!
                counter++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw  new RuntimeException("STOPPED");
    }

    public void stop() {
        running = false;
        if (bool)
            System.out.println(counter);
    }
}
