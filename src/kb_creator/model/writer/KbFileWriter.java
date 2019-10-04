package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class KbFileWriter extends AbstractKbWriter {


    private WriterThread consistentWriter;
    private WriterThread inconsistentWriter;


    public KbFileWriter(String filePathToSave, BlockingQueue<AbstractKnowledgeBase> consistentQueue, BlockingQueue<AbstractKnowledgeBase> inconsistentQueue) {


        this.consistentWriter = new WriterThread(filePathToSave, "consistent", consistentQueue);
        Thread consistentWriterThread = new Thread(consistentWriter);
        consistentWriterThread.start();

        this.inconsistentWriter = new WriterThread(filePathToSave, "inconsistent", inconsistentQueue);
        Thread inconsistentWriterThread = new Thread(inconsistentWriter);
        inconsistentWriterThread.start();

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
        inconsistentWriter.stop();
        status = WriterStatus.STOPPED;
    }

}

