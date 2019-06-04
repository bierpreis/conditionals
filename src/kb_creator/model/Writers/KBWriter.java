package kb_creator.model.Writers;

import kb_creator.model.Conditionals.KnowledgeBase;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KBWriter implements Runnable {
    private Queue<KnowledgeBase> consistentQueue;
    private Queue<KnowledgeBase> inconsistentQueue;
    private String rootFilePath;

    private int consitentCounter;
    private int inconsistentCounter;

    private int lastConsitentAmount;
    private long nextSpeedcalculationTime;
    private final long SPEED_CALCULATION_INTERVAL = 5000;

    private int consistentSpeed;
    private int inconsistentSpeed;

    public void run() {

        while (true) {
            //rootfilepath is null when saving is not requested. maybe improve by one abstract writer and one like this and one fake
            if (rootFilePath != null) {
                System.out.println("iteration");
                calculateConsistentSpeed();
                if (!consistentQueue.isEmpty())
                    writeConsistentKBToFile(consistentQueue.poll());
                if (!inconsistentQueue.isEmpty())
                    writeInconsistentKBToFile(inconsistentQueue.poll());
                if (consistentQueue.isEmpty() && inconsistentQueue.isEmpty()) try {
                    System.out.println("sleep inc: " + inconsistentQueue.size() + " cons: " + consistentQueue.size());
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }


    public KBWriter(String filePathToSave) {
        consitentCounter = 0;
        inconsistentCounter = 0;
        nextSpeedcalculationTime = System.currentTimeMillis() + SPEED_CALCULATION_INTERVAL;

        if (filePathToSave != null) {
            rootFilePath = filePathToSave;

            consistentQueue = new ConcurrentLinkedQueue();
            inconsistentQueue = new ConcurrentLinkedQueue();

        }
    }

    public void addConsistentKB(KnowledgeBase kbToAdd) {
        if (consistentQueue != null)
            consistentQueue.add(kbToAdd);
    }

    public void addInconsistentKB(KnowledgeBase kbToAdd) {
        //System.out.println("adding inConsistent");
        if (inconsistentQueue != null)
            inconsistentQueue.add(kbToAdd);
    }

    private void writeConsistentKBToFile(KnowledgeBase knowledgeBase) {
        long beginningTime = 0;
        if (rootFilePath != null) {
            String filePath = rootFilePath + knowledgeBase.getSize() + "/" + "consistent/";
            try {

                File consistentFolder = new File(filePath);
                consistentFolder.mkdirs();

                PrintWriter writer = new PrintWriter(filePath + knowledgeBase.getKbNumber() + ".txt", "UTF-8");

                writer.print(knowledgeBase.toFileString());
                beginningTime = System.currentTimeMillis();
                writer.close(); //todo: this takes about 120 ms. fix it!

                System.out.println("written consistent kb");
                consitentCounter++;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("writing time: " + (System.currentTimeMillis() - beginningTime));
    }

    private void writeInconsistentKBToFile(KnowledgeBase knowledgeBase) {
        if (rootFilePath != null) {
            String filePath = rootFilePath + knowledgeBase.getSize() + "/" + "inconsistent/";

            File inconsistentFolder = new File(filePath);
            inconsistentFolder.mkdirs();
            System.out.println("written inconsistent kb");
            try {

                PrintWriter writer = new PrintWriter(filePath + knowledgeBase.getKbNumber() + ".txt", "UTF-8");
                writer.print(knowledgeBase.toFileString());
                writer.close();
                inconsistentCounter++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getConsistentQueue() {
        return consistentQueue.size();
    }

    public int getInconsistetnQueue() {
        return inconsistentQueue.size();
    }

    private void calculateConsistentSpeed() {
        if (System.currentTimeMillis() > nextSpeedcalculationTime) {
            int kbsSinceLastCalculation = consitentCounter - lastConsitentAmount;
            int speed = kbsSinceLastCalculation / (int) (SPEED_CALCULATION_INTERVAL / 1000);
            lastConsitentAmount = consitentCounter;
            nextSpeedcalculationTime = System.currentTimeMillis() + SPEED_CALCULATION_INTERVAL;
            System.out.println("Speed: " + speed);
        }
    }

    public int getConsitentCounter() {
        return consitentCounter;
    }

    public int getInconsistentCounter() {
        return inconsistentCounter;
    }

}

