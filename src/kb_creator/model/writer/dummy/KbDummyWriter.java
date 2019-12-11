package kb_creator.model.writer.dummy;

import kb_creator.model.writer.AbstractKbWriter;
import kb_creator.model.writer.WriterStatus;


//this class is a writer which throws all input away
//it is a kind of placeholder for test runs
public class KbDummyWriter extends AbstractKbWriter {


    private DummyWriterThread consistentThreadObject;

    private DummyWriterThread inconsistentThreadObject;

    public KbDummyWriter() {
        System.out.println("new dummy KbWriter");
        status = WriterStatus.NOT_STARTED;

        consistentThreadObject = new DummyWriterThread(consistentWriterQueue);
        inconsistentThreadObject = new DummyWriterThread(inconsistentWriterQueue);

        consistentThread = new Thread(consistentThreadObject);
        consistentThread.setName("ConsistentDummyKbWriterThread");
        consistentThread.start();

        inconsistentThread = new Thread(inconsistentThreadObject);
        inconsistentThread.setName("InconsistentDummyKbWriterThread");
        inconsistentThread.start();

        status = WriterStatus.DUMMY_WRITER;
    }


    @Override
    public void stopThreads() {
        consistentThreadObject.stopLoop();
        inconsistentThreadObject.stopLoop();


        consistentThread.interrupt();
        inconsistentThread.interrupt();
    }

    @Override
    public void finishAndStopThreads() {
        //intentionally this. methods are different in real writer.
        stopThreads();
    }

    @Override
    public void waitUntilAllKbsWritten() {
        //intentionally nothing
    }

    @Override
    public void prepareIteration(int k) {
        inconsistentThreadObject.newIteration();
        consistentThreadObject.newIteration();
    }

    //getter

    @Override
    public int getIterationConsistentCounter() {
        return consistentThreadObject.getIterationCounter();
    }

    @Override
    public int getIterationInconsistentCounter() {
        return inconsistentThreadObject.getIterationCounter();
    }

    @Override
    public int getTotalInconsistentCounter() {
        return inconsistentThreadObject.getTotalCounter();
    }

    @Override
    public int getTotalConsistentCounter() {
        return consistentThreadObject.getTotalCounter();
    }


}
