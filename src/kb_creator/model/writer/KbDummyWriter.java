package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

import java.util.concurrent.BlockingQueue;


//this class is a writer which throws all input away
//it is a kind of placeholder for test runs
public class KbDummyWriter extends AbstractKbWriter {


    private DummyWriterThread consistentThreadObject;
    private Thread consistentThread;

    private DummyWriterThread inconsistentThreadObject;
    private Thread inconsistentThread;

    public KbDummyWriter(BlockingQueue<AbstractKnowledgeBase> consistentKbQueue, BlockingQueue<AbstractKnowledgeBase> inconsistentKbQueue) {
        System.out.println("new dummy KbWriter");
        status = WriterStatus.NOT_STARTED;

        consistentThreadObject = new DummyWriterThread(consistentKbQueue);
        inconsistentThreadObject = new DummyWriterThread(inconsistentKbQueue);

        consistentThread = new Thread(consistentThreadObject);
        consistentThread.setName("ConsistentDummyKbWriterThread");
        consistentThread.start();

        inconsistentThread = new Thread(inconsistentThreadObject);
        inconsistentThread.setName("InconsistentDummyKbWriterThread");
        inconsistentThread.start();


    }


    @Override
    public int getTotalInconsistentCounter() {
        return inconsistentThreadObject.getTotalCounter();
    }

    @Override
    public int getTotalConsistentCounter() {
        return consistentThreadObject.getTotalCounter();
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
    public void stopThreads() {
        System.out.println("!!tried to stop queue threads");
        consistentThreadObject.stopLoop();
        inconsistentThreadObject.stopLoop();


        consistentThread.interrupt();
        inconsistentThread.interrupt();
    }

    @Override
    public void finishIteration() {
        //intentionally nothing
    }

    @Override
    public int getIterationConsistentCounter() {
        return consistentThreadObject.getIterationCounter();
    }

    @Override
    public int getIterationInconsistentCounter() {
        return inconsistentThreadObject.getIterationCounter();
    }

    @Override
    public void newIteration() {
        inconsistentThreadObject.newIteration();
        consistentThreadObject.newIteration();
    }

}
