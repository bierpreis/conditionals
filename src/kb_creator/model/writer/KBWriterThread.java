package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class KBWriterThread implements Runnable {
    private String folderName;
    private String rootFilePath;
    private BlockingQueue<AbstractKnowledgeBase> queue;
    private boolean running = true;

    private int iterationCounter = 0;
    private int totalCounter = 0;

    public KBWriterThread(String rootFilePath, String folderName, BlockingQueue<AbstractKnowledgeBase> queue) {
        this.folderName = folderName;
        this.queue = queue;
        this.rootFilePath = rootFilePath;
    }

    //todo: this thread is not closed by stop. return on interrupt?!
    @Override
    public void run() {
        System.out.println("New Writer Thread started for " + folderName + " kbs");
        while (running) {
            try {
                writeConsistentKbToFile(queue.take());
            } catch (InterruptedException e) {
                //intentionally nothing
            }
        }
        System.out.println("Writer Thread closed for " + folderName + " kbs");
    }

    public void finishIteration() {
        while (!queue.isEmpty()) {
            try {
                Thread.sleep(100);
                System.out.println("sleeping");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void newIteration() {
        iterationCounter = 0;
    }

    private void writeConsistentKbToFile(AbstractKnowledgeBase knowledgeBase) {


        String filePath = rootFilePath + knowledgeBase.getSize() + "/" + folderName + "/";
        try {
            iterationCounter++;
            totalCounter++;

            knowledgeBase.setKbNumber(iterationCounter);
            File consistentFolder = new File(filePath);
            consistentFolder.mkdirs();

            //todo: this line throws exception when hdd is full.
            //java.io.FileNotFoundException: /home/bierpreis/KBs/3/consistent/8061370.txt (Auf dem Gerät ist kein Speicherplatz mehr verfügbar)
            PrintWriter writer = new PrintWriter(filePath + knowledgeBase.getKbNumber() + ".txt", "UTF-8");
            writer.print(knowledgeBase.toFileString());

            writer.close();


        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public int getIterationCounter() {
        return iterationCounter;
    }

    public int getTotalCounter() {
        return totalCounter;
    }

    public int getSize() {
        return queue.size();
    }

    public void finishAndStopLoop() {
        System.out.println("closing " + folderName);
        //this makes sure queue is empty before it stops
        while (!queue.isEmpty())
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        running = false;
    }

    public void stopLoop() {
        System.out.println("closing " + folderName);
        //this makes sure queue is empty before it stops

        running = false;
        System.out.println("closing " + folderName + "finished");
    }
}
