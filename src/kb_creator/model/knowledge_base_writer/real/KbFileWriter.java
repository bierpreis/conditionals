package kb_creator.model.knowledge_base_writer.real;

import kb_creator.model.knowledge_base_writer.AbstractKbWriter;
import kb_creator.model.knowledge_base_writer.WriterStatus;
import kb_creator.model.propositional_logic.KnowledgeBase;

import java.util.concurrent.BlockingQueue;

public class KbFileWriter extends AbstractKbWriter {

    private KBWriterThread consistentWriter;
    private KBWriterThread inconsistentWriter;

    private Thread consistentWriterThread;
    private Thread inconsistentWriterThread;

    public KbFileWriter(String filePathToSave, BlockingQueue<KnowledgeBase> consistentQueue, BlockingQueue<KnowledgeBase> inconsistentQueue) {

        this.consistentWriter = new KBWriterThread(filePathToSave, "consistent", consistentQueue);
        consistentWriterThread = new Thread(consistentWriter);
        consistentWriterThread.setName("ConsistentKbWriter");
        consistentWriterThread.start();


        this.inconsistentWriter = new KBWriterThread(filePathToSave, "inconsistent", inconsistentQueue);
        inconsistentWriterThread = new Thread(inconsistentWriter);
        inconsistentWriterThread.setName("InconsistentKbWriter");
        inconsistentWriterThread.start();

        status = WriterStatus.RUNNING;
    }


    public void stopThreads() {

        consistentWriter.stopLoop();
        consistentWriterThread.interrupt();

        inconsistentWriter.stopLoop();
        inconsistentWriterThread.interrupt();
        status = WriterStatus.STOPPED;
        System.out.println("main kb writer thread stopped");
    }

    public void finishAndStopThreads() {

        consistentWriter.finishAndStopLoop();
        consistentWriterThread.interrupt();

        inconsistentWriter.finishAndStopLoop();
        inconsistentWriterThread.interrupt();
        status = WriterStatus.STOPPED;
        System.out.println("main kb writer thread stopped");
    }



    @Override
    public void finishIteration() {
        consistentWriter.finishIteration();
        inconsistentWriter.finishIteration();
    }

    @Override
    public void newIteration(int k) {
        consistentWriter.newIteration(k);
        inconsistentWriter.newIteration(k);
    }


    //getters

    @Override
    public int getIterationConsistentCounter() {
        return consistentWriter.getIterationCounter();
    }

    @Override
    public int getIterationInconsistentCounter() {
        return inconsistentWriter.getIterationCounter();
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
}

