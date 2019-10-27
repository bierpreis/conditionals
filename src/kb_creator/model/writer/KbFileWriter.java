package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

import java.util.concurrent.BlockingQueue;

public class KbFileWriter extends AbstractKbWriter {


    private KBWriterThread consistentWriter;
    private KBWriterThread inconsistentWriter;

    private Thread consistentWriterThread;
    private Thread inconsistentWriterThread;

    //todo: check if writer gets stopped correctly
    public KbFileWriter(String filePathToSave, BlockingQueue<AbstractKnowledgeBase> consistentQueue, BlockingQueue<AbstractKnowledgeBase> inconsistentQueue) {

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
    public int getTotalConsistentCounter() {
        return consistentWriter.getTotalCounter();
    }

    @Override
    public int getTotalInconsistentCounter() {
        return inconsistentWriter.getTotalCounter();
    }

    public void stopThreads() {

        consistentWriter.finishAndStopLoop();
        consistentWriterThread.interrupt();

        inconsistentWriter.finishAndStopLoop();
        inconsistentWriterThread.interrupt();
        status = WriterStatus.STOPPED;
        System.out.println("main kb writer thread stopped");
    }


    @Override
    public int getIterationConsistentCounter() {
        return consistentWriter.getIterationCounter();
    }

    @Override
    public int getIterationInconsistentCounter() {
        return inconsistentWriter.getIterationCounter();
    }


    @Override
    public void finishIteration() {
        consistentWriter.finishIteration();
        inconsistentWriter.finishIteration();
    }

    @Override
    public void newIteration() {
        consistentWriter.newIteration();
        inconsistentWriter.newIteration();
    }

}

