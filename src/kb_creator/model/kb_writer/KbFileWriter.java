package kb_creator.model.kb_writer;

import kb_creator.model.conditionals.knowledge_base.AbstractKnowledgeBase;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class KbFileWriter extends AbstractKbWriter implements Runnable {
    private Queue<AbstractKnowledgeBase> consistentQueue;
    private Queue<AbstractKnowledgeBase> inconsistentQueue;
    private String rootFilePath;

    private int consistentCounter;
    private int inconsistentCounter;


    @Override
    public void run() {

        while (true) {


            if (!consistentQueue.isEmpty())
                writeConsistentKbToFile(consistentQueue.poll());
            if (!inconsistentQueue.isEmpty())
                writeInconsistentKBToFile(inconsistentQueue.poll());
            if (consistentQueue.isEmpty() && inconsistentQueue.isEmpty()) try {
                Thread.sleep(500);
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

            //todo: check if this or linkedBlockingQueue is better
            //idea: creator creates list, then gives whole list to writer and creates new list
            //idea: first put 1 mio then write 1 mio
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

}

