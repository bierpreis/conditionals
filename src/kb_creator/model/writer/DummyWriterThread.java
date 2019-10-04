package kb_creator.model.writer;

import java.util.concurrent.BlockingQueue;

public class DummyWriterThread implements Runnable {
    private volatile boolean running = true;
    private BlockingQueue queue;

    public DummyWriterThread(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (running) {
            //this should delete all entries and wait if there are no entries
            queue.poll();
        }
    }

    public void stop() {
        running = false;
    }
}
