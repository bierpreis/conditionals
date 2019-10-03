package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class KbFileWriter extends AbstractKbWriter implements Runnable {
    private Queue<AbstractKnowledgeBase> consistentQueue;
    private Queue<AbstractKnowledgeBase> inconsistentQueue;
    private String rootFilePath;
    private volatile boolean running = true;

    private int consistentCounter;
    private int inconsistentCounter;

    //todo: idea: make 1 runnable class as writer. than 2 objects of it 1 for consistent and 1 for insconsistent writer

    @Override
    public void run() {
        while (running) {
            if (!consistentQueue.isEmpty()) {
                status = WriterStatus.RUNNING;
                writeConsistentKbToFile(consistentQueue.poll());
            } else if (!inconsistentQueue.isEmpty()) {
                status = WriterStatus.RUNNING;
                writeInconsistentKBToFile(inconsistentQueue.poll());
            } else try {
                status = WriterStatus.SLEEPING;
                Thread.sleep(500); //todo: sleep is shit.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


    public KbFileWriter(String filePathToSave) {
        consistentCounter = 0;
        inconsistentCounter = 0;

        if (filePathToSave != null) {
            rootFilePath = filePathToSave;

            consistentQueue = new LinkedBlockingQueue<>();
            inconsistentQueue = new LinkedBlockingQueue<>();

        }
    }

    @Override
    public void addConsistentKb(AbstractKnowledgeBase kbToAdd) {
        consistentQueue.add(kbToAdd);
    }

    @Override
    public void addInconsistentKb(AbstractKnowledgeBase kbToAdd) {
        inconsistentQueue.add(kbToAdd);
    }

    private void writeConsistentKbToFile(AbstractKnowledgeBase knowledgeBase) {


        String filePath = rootFilePath + knowledgeBase.getSize() + "/" + "consistent/";
        try {

            File consistentFolder = new File(filePath);
            consistentFolder.mkdirs();

            PrintWriter writer = new PrintWriter(filePath + knowledgeBase.getKbNumber() + ".txt", "UTF-8");

            writer.print(knowledgeBase.toFileString());

            writer.close();
            consistentCounter++;

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void writeInconsistentKBToFile(AbstractKnowledgeBase knowledgeBase) {

        String filePath = rootFilePath + knowledgeBase.getSize() + "/" + "inconsistent/";

        File inconsistentFolder = new File(filePath);
        inconsistentFolder.mkdirs();
        try {

            PrintWriter writer = new PrintWriter(filePath + knowledgeBase.getKbNumber() + ".txt", "UTF-8");
            writer.print(knowledgeBase.toFileString());
            writer.close();
            inconsistentCounter++;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getConsistentQueue() {

        return consistentQueue.size();
    }

    @Override
    public int getInconsistentQueue() {
        return inconsistentQueue.size();
    }

    @Override
    public int getConsistentCounter() {
        return consistentCounter;
    }

    @Override
    public int getInconsistentCounter() {
        return inconsistentCounter;
    }

    public void stop() {
        status = WriterStatus.STOPPED;
        running = false;
    }

}

