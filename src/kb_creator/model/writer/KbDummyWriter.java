package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

import java.util.concurrent.BlockingQueue;


//this class is a writer which throws all input away
//it is a kind of placeholder for test runs
public class KbDummyWriter extends AbstractKbWriter {
    private BlockingQueue consistentKbQueue;
    private BlockingQueue inconsistentKbQueue;

    private DummyWriterThread consistentThreadObject;
    private Thread consistentThread;

    private DummyWriterThread inconsistentThreadObject;
    private Thread inconsistentThread;

    public KbDummyWriter(BlockingQueue<AbstractKnowledgeBase> consistentKbQueue, BlockingQueue<AbstractKnowledgeBase> inconsistentKbQueue) {
        System.out.println("new dummy writer");
        status = WriterStatus.NOT_STARTED;

        this.consistentKbQueue = consistentKbQueue;
        this.inconsistentKbQueue = inconsistentKbQueue;

        consistentThreadObject = new DummyWriterThread(consistentKbQueue);
        inconsistentThreadObject = new DummyWriterThread(inconsistentKbQueue);

        consistentThread = new Thread(consistentThreadObject);
        consistentThread.setName("ConsistentDummyWriterThread");
        consistentThread.start();

        inconsistentThread = new Thread(inconsistentThreadObject);
        inconsistentThread.setName("InconsistentDummyWriterThread");
        inconsistentThread.start();


    }


    @Override
    public int getInconsistentCounter() {
        return inconsistentThreadObject.getCounter();
    }

    @Override
    public int getConsistentCounter() {
        return consistentThreadObject.getCounter();
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

        consistentThreadObject.stopLoop();
        inconsistentThreadObject.stopLoop();

        System.out.println("consistent counter: " + consistentThreadObject.getCounter());
        System.out.println("inconsistent counter: " + inconsistentThreadObject.getCounter());

        consistentThread.interrupt();
        inconsistentThread.interrupt();

    }

}
