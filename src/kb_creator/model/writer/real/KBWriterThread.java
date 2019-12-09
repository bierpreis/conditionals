package kb_creator.model.writer.real;

import kb_creator.model.logic.KnowledgeBase;

import java.io.*;
import java.util.concurrent.BlockingQueue;

public class KBWriterThread implements Runnable {

    private String subFolderName;
    private String rootFilePath;
    private String currentIterationFilePath;

    private BlockingQueue<KnowledgeBase> queue;
    private boolean running = true;

    private int iterationCounter = 0;
    private int totalCounter = 0;

    //todo: leading zeroes. option in gui for that.
    public KBWriterThread(String rootFilePath, String subFolderName, BlockingQueue<KnowledgeBase> queue) {
        this.subFolderName = subFolderName;
        this.queue = queue;
        this.rootFilePath = rootFilePath;
    }

    @Override
    public void run() {
        System.out.println("new writer thread started for " + subFolderName + " kbs");
        while (running) {
            try {
                writeKbToFile(queue.take());
            } catch (InterruptedException e) {
                //intentionally nothing
            }
        }
        System.out.println("writer thread closed for " + subFolderName + " kbs");
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
        currentIterationFilePath = rootFilePath + (k) + "/" + subFolderName + "/";
        File consistentFolder = new File(currentIterationFilePath);
        consistentFolder.mkdirs();
    }


    private void writeKbToFile(KnowledgeBase knowledgeBase) {

        iterationCounter++;
        totalCounter++;

        PrintWriter writer;
        //leading zeroes would look like this:
        //File fileToRead = new File(folderToRead + "/" + String.format(numberOfDigitsString, readingFileNameCounter) + ".txt");

        //this will trigger when hdd space is full or there are too much files
        try {
            writer = new PrintWriter(currentIterationFilePath + knowledgeBase.getNumber() + ".txt", "UTF-8");
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
        System.out.println("closing " + subFolderName);
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
        System.out.println("closing " + subFolderName);
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
