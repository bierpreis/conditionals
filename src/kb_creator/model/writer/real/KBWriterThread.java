package kb_creator.model.writer.real;

import kb_creator.model.logic.KnowledgeBase;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class KBWriterThread implements Runnable {

    private String subFolderName;
    private String rootFilePath;
    private String currentIterationFilePath;

    private BlockingQueue<KnowledgeBase> queue;
    private boolean running = true;

    private int iterationCounter = 0;
    private int totalCounter = 0;

    private String numberOfDigitsString;

    private int requestedKbNumber;

    public KBWriterThread(String rootFilePath, String subFolderName, BlockingQueue<KnowledgeBase> queue, int requestedFileNameLength, int requestedKbNumber) {
        this.subFolderName = subFolderName;
        this.queue = queue;
        this.rootFilePath = rootFilePath;
        this.numberOfDigitsString = "%0" + requestedFileNameLength + "d";
        this.requestedKbNumber = requestedKbNumber;
    }

    //todo: counters not working!
    @Override
    public void run() {
        System.out.println("new writer thread started for " + subFolderName + " kbs");
        while (running) {
            List<KnowledgeBase> kbList = new ArrayList<>(requestedKbNumber);

            boolean interrupted = false;
            int counter = 0;
            while (counter < requestedKbNumber && !interrupted)
                try {
                    kbList.add(queue.take());
                    counter++;
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            writeKbListToFile(kbList);
        }
        System.out.println("writer thread closed for " + subFolderName + " kbs");
    }


    public void waitUntilAllKbsWritten() {
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


    private void writeKbListToFile(List<KnowledgeBase> kbList) {

        iterationCounter++;
        totalCounter++;

        PrintWriter writer;
        try {
            writer = new PrintWriter(currentIterationFilePath + String.format(numberOfDigitsString, kbList.get(0).getNumber()) + ".txt", "UTF-8");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
            return; //just to hide compiler warning
        } catch (UnsupportedEncodingException ue) {
            ue.printStackTrace();
            return;
        }


        writer.println("signature\n");
        writer.append(kbList.get(0).getSignature().toString().toLowerCase());
        writer.append("\n\n");

        for (KnowledgeBase knowledgeBase : kbList) {
            writer.append(knowledgeBase.toFileString()); //todo: here was print. whats difference print vs append?
        }
        writer.flush();
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
