package kb_creator.model.writer.real;

import kb_creator.model.writer.AbstractKbWriter;
import kb_creator.model.writer.KbWriterOptions;
import kb_creator.model.writer.WriterStatus;


public class KbFileWriter extends AbstractKbWriter {

    private KbWriterThread consistentWriter;
    private KbWriterThread inconsistentWriter;


    public KbFileWriter(KbWriterOptions writerOptions) {



        this.consistentWriter = new KbWriterThread( "consistent", consistentWriterQueue,  writerOptions);
        consistentThread = new Thread(consistentWriter);
        consistentThread.setName("ConsistentKbWriter");
        consistentThread.start();


        this.inconsistentWriter = new KbWriterThread("inconsistent", inconsistentWriterQueue, writerOptions);
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
        consistentThread.interrupt();
        inconsistentWriter.finishIteration();
        inconsistentThread.interrupt();
    }

    @Override
    public void prepareIteration(int k) {
        consistentWriter.newIteration(k);
        inconsistentWriter.newIteration(k);
    }


    //getters

    @Override
    public long getIterationConsistentCounter() {
        return consistentWriter.getIterationCounter();
    }

    @Override
    public long getIterationInconsistentCounter() {
        return inconsistentWriter.getIterationCounter();
    }


    @Override
    public long getTotalConsistentCounter() {
        return consistentWriter.getTotalCounter();
    }

    @Override
    public long getTotalInconsistentCounter() {
        return inconsistentWriter.getTotalCounter();
    }
}

