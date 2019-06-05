package kb_creator.model.Writers.KBWriter;

import kb_creator.model.Conditionals.KnowledgeBase;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KbFileWriter extends AbstractKbWriter implements Runnable {
    private Queue<KnowledgeBase> consistentQueue;
    private Queue<KnowledgeBase> inconsistentQueue;
    private String rootFilePath;

    private int consistentCounter;
    private int inconsistentCounter;

    private int lastConsistentAmount;
    private long nextSpeedCalculationTime;
    private final long SPEED_CALCULATION_INTERVAL = 5000;

    @Override
    public void run() {

        while (true) {
            //rootfilepath is null when saving is not requested. maybe improve by one abstract writer and one like this and one fake


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
        nextSpeedCalculationTime = System.currentTimeMillis() + SPEED_CALCULATION_INTERVAL;

        if (filePathToSave != null) {
            rootFilePath = filePathToSave;

            consistentQueue = new ConcurrentLinkedQueue();
            inconsistentQueue = new ConcurrentLinkedQueue();

        }
    }

    @Override
    public void addConsistentKb(KnowledgeBase kbToAdd) {
            consistentQueue.add(kbToAdd);
    }

    @Override
    public void addInconsistentKb(KnowledgeBase kbToAdd) {
            inconsistentQueue.add(kbToAdd);
    }

    private void writeConsistentKbToFile(KnowledgeBase knowledgeBase) {


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

    private void writeInconsistentKBToFile(KnowledgeBase knowledgeBase) {

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

