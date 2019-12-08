package kb_creator.model.knowledge_base_writer.real;

import kb_creator.model.knowledge_base_writer.AbstractKbWriter;
import kb_creator.model.knowledge_base_writer.WriterStatus;


public class KbFileWriter extends AbstractKbWriter {

    private KBWriterThread consistentWriter;
    private KBWriterThread inconsistentWriter;

    //todo: maybe leading zeroes option?
    public KbFileWriter(String filePathToSave) {

        this.consistentWriter = new KBWriterThread(filePathToSave, "consistent", consistentWriterQueue);
        consistentThread = new Thread(consistentWriter);
        consistentThread.setName("ConsistentKbWriter");
        consistentThread.start();


        this.inconsistentWriter = new KBWriterThread(filePathToSave, "inconsistent", inconsistentWriterQueue);
        inconsistentThread = new Thread(inconsistentWriter);
        inconsistentThread.setName("InconsistentKbWriter");
        inconsistentThread.start();

        status = WriterStatus.RUNNING;
    }


    public void stopThreads() {

        consistentWriter.stopLoop();
        consistentThread.interrupt();

        inconsistentWriter.stopLoop();
        inconsistentThread.interrupt();
        status = WriterStatus.STOPPED;
        System.out.println("main kb writer thread stopped");
    }

    public void finishAndStopThreads() {

        consistentWriter.finishAndStopLoop();
        consistentThread.interrupt();

        inconsistentWriter.finishAndStopLoop();
        inconsistentThread.interrupt();
        status = WriterStatus.STOPPED;
        System.out.println("main kb writer thread stopped");
    }



    @Override
    public void finishIteration() {
        consistentWriter.finishIteration();
        inconsistentWriter.finishIteration();
    }

    @Override
    public void prepareIteration(int k) {
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
    public int getTotalConsistentCounter() {
        return consistentWriter.getTotalCounter();
    }

    @Override
    public int getTotalInconsistentCounter() {
        return inconsistentWriter.getTotalCounter();
    }
}
