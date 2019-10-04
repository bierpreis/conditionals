package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

import java.util.concurrent.BlockingQueue;


//this class is a writer which throws all input away
//it is a kind of placeholder for test runs
public class KbDummyWriter extends AbstractKbWriter implements Runnable {
    private BlockingQueue consistentKbQueue;
    private BlockingQueue inconsistentKbQueue;

    private DummyWriterThread consistentThread;
    private DummyWriterThread inconsistentThread;

    public KbDummyWriter(BlockingQueue<AbstractKnowledgeBase> consistentKbQueue, BlockingQueue<AbstractKnowledgeBase> inconsistentKbQueue) {
        status = WriterStatus.NOT_STARTED;

        this.consistentKbQueue = consistentKbQueue;
        this.inconsistentKbQueue = inconsistentKbQueue;

        consistentThread = new DummyWriterThread(consistentKbQueue);
        inconsistentThread = new DummyWriterThread(inconsistentKbQueue);

        new Thread(consistentThread).start();
        new Thread(inconsistentThread);


    }


    @Override
    public void run() {
        while (running) {

            //this empties the queue so the creator cann put stuff in there again
            if (!consistentKbQueue.isEmpty())
                consistentKbQueue.poll();
            if (inconsistentKbQueue.isEmpty())
                inconsistentKbQueue.poll();
        }
    }

    @Override
    public int getInconsistentCounter() {
        return 0;
    }

    @Override
    public int getConsistentCounter() {
        return 0;
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
    }

}
