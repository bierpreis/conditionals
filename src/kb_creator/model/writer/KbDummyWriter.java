package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;


//this class is a writer which throws all input away
//it is a kind of placeholder for test runs
public class KbDummyWriter extends AbstractKbWriter implements Runnable {
    private BlockingQueue consistentKbQueue;
    private BlockingQueue inconsistentKbQueue;

    private DummyWriterThread consistentThread;
    private DummyWriterThread inconsistentThread;

    int consistentCounter = 0;
    int inconsistentCounter = 0;

    public KbDummyWriter(BlockingQueue<AbstractKnowledgeBase> consistentKbQueue, BlockingQueue<AbstractKnowledgeBase> inconsistentKbQueue) {
        System.out.println("new dummy writer");
        status = WriterStatus.NOT_STARTED;

        this.consistentKbQueue = consistentKbQueue;
        this.inconsistentKbQueue = inconsistentKbQueue;

        consistentThread = new DummyWriterThread(consistentKbQueue, true);
        inconsistentThread = new DummyWriterThread(inconsistentKbQueue);

        new Thread(consistentThread).start();
        new Thread(inconsistentThread).start();


    }

    //todo: this thread is never started?!
    @Override
    public void run() {
        status = WriterStatus.RUNNING;
        while (running) {
            System.out.println("consistent counter: " + consistentCounter);
            //this empties the queue so the creator cann put stuff in there again
            if (!consistentKbQueue.isEmpty()) {
                consistentKbQueue.poll();
                consistentCounter++;

            }
            if (!inconsistentKbQueue.isEmpty()) {
                inconsistentKbQueue.poll();
                inconsistentCounter++;
            }
        }
    }

    //todo: use this counters?
    @Override
    public int getInconsistentCounter() {
        return inconsistentCounter;
    }

    @Override
    public int getConsistentCounter() {
        return consistentCounter;
    }


    @Override
    public int getConsistentQueue() {
        return 0;
    }

    @Override
    public int getInconsistentQueue() {
        return 0;
    }

    @Override
    public void stop() {
        consistentThread.stop();
        inconsistentThread.stop();
        System.out.println("consistent counter: " + consistentCounter);
    }

}
