package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

import java.io.*;
import java.util.concurrent.BlockingQueue;

public class KBWriterThread implements Runnable {
    private String folderName;
    private String rootFilePath;
    private BlockingQueue<AbstractKnowledgeBase> queue;
    private boolean running = true;

    private int iterationCounter = 0;
    private int totalCounter = 0;

    private String filePath;

    public KBWriterThread(String rootFilePath, String folderName, BlockingQueue<AbstractKnowledgeBase> queue) {
        this.folderName = folderName;
        this.queue = queue;
        this.rootFilePath = rootFilePath;
    }

    //todo: start and stop iterations here
    @Override
    public void run() {
        System.out.println("New Writer Thread started for " + folderName + " kbs");
        while (running) {
            try {
                writeKbToFile(queue.take());
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
                System.out.println("writer finishing iteration..");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void newIteration(int k) {
        iterationCounter = 0;
        filePath = rootFilePath + (k) + "/" + folderName + "/";
        File consistentFolder = new File(filePath);
        consistentFolder.mkdirs();
    }


    private void writeKbToFile(AbstractKnowledgeBase knowledgeBase) {

        iterationCounter++;
        totalCounter++;

        knowledgeBase.setKbNumber(iterationCounter); //todo: this should not be here but it is because fucked multi threading

        PrintWriter writer;

        //this will trigger when hdd space is full or there are too much files
        try {
            writer = new PrintWriter(filePath + knowledgeBase.getKbNumber() + ".txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
            return; //just to hide compiler warning
        } catch (UnsupportedEncodingException ue) {
            ue.printStackTrace();
            return;
        }
        writer.print(knowledgeBase.toFileString());
        writer.close();
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
    }

    //getters

    public int getIterationCounter() {
        return iterationCounter;
    }

    public int getTotalCounter() {
        return totalCounter;
    }

    public int getSize() {
        return queue.size();
    }
}
