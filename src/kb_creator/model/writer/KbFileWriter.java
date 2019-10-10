package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

import java.util.concurrent.BlockingQueue;

public class KbFileWriter extends AbstractKbWriter {


    private KBWriterThread consistentWriter;
    private KBWriterThread inconsistentWriter;

    private Thread consistentWriterThread;
    private Thread inconsistentWriterThread;


    public KbFileWriter(String filePathToSave, BlockingQueue<AbstractKnowledgeBase> consistentQueue, BlockingQueue<AbstractKnowledgeBase> inconsistentQueue) {

        //todo: the 2 threads are not closed correctly! impossible with local thread variable!
        this.consistentWriter = new KBWriterThread(filePathToSave, "consistent", consistentQueue);
        consistentWriterThread = new Thread(consistentWriter);
        consistentWriterThread.start();

        this.inconsistentWriter = new KBWriterThread(filePathToSave, "inconsistent", inconsistentQueue);
        inconsistentWriterThread = new Thread(inconsistentWriter);
        inconsistentWriterThread.start();

        status = WriterStatus.RUNNING;
    }


    @Override
    public int getConsistentQueue() {
        return consistentWriter.getSize();

    }

    @Override
    public int getInconsistentQueue() {
        return inconsistentWriter.getSize();
    }

    @Override
    public int getConsistentCounter() {
        return consistentWriter.getCounter();
    }

    @Override
    public int getInconsistentCounter() {
        return inconsistentWriter.getCounter();
    }

    public void stop() {

        consistentWriter.stop();
        consistentWriterThread.interrupt();

        inconsistentWriter.stop();
        inconsistentWriterThread.interrupt();
        status = WriterStatus.STOPPED;
        System.out.println("main kb writer thread stopped");
    }

}

