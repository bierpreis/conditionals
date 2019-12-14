package kb_creator.model.writer.real;

import kb_creator.model.logic.KnowledgeBase;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class KbWriterThread implements Runnable {

    private String subFolderName;
    private String rootFilePath;
    private String currentIterationFilePath;

    private BlockingQueue<KnowledgeBase> queue;
    private boolean running = true;

    private int iterationCounter = 0;
    private int totalCounter = 0;

    private String numberOfDigitsString;

    private int requestedKbNumber;

    private volatile boolean flushRequested = false;

    public KbWriterThread(String rootFilePath, String subFolderName, BlockingQueue<KnowledgeBase> queue, int requestedFileNameLength, int requestedKbNumber) {
        this.subFolderName = subFolderName;
        this.queue = queue;
        this.rootFilePath = rootFilePath;
        this.numberOfDigitsString = "%0" + requestedFileNameLength + "d";
        this.requestedKbNumber = requestedKbNumber;
    }


    @Override
    public void run() {
        System.out.println("new writer thread started for " + subFolderName + " kbs");
        while (running) {
            List<KnowledgeBase> kbList = new ArrayList<>(requestedKbNumber);

            int counter = 0;
            while (counter < requestedKbNumber && !flushRequested)
                try {
                    kbList.add(queue.take());
                    counter++;
                } catch (InterruptedException e) {
                    flushRequested = true;
                }
            writeKbListToFile(kbList);
        }
        System.out.println("writer thread closed for " + subFolderName + " kbs");
    }


    public void finishIteration() {

        while (queue.size() > requestedKbNumber) {
            try {
                Thread.sleep(100);

                System.out.println("writer finishing iteration..");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        flushRequested = true;

        while (!queue.isEmpty()) {
            try {
                //todo: sometimes this wont finish
                Thread.sleep(500);
                System.out.println("waiting for kb writer flush to happen...!!!");
            } catch (InterruptedException e) {
                return; //this should only happen by gui stop button.
            }
        }
    }

    public void newIteration(int k) {
        flushRequested = false;
        iterationCounter = 0;
        currentIterationFilePath = rootFilePath + (k) + "/" + subFolderName + "/";
        File consistentFolder = new File(currentIterationFilePath);
        consistentFolder.mkdirs();
    }


    private void writeKbListToFile(List<KnowledgeBase> kbList) {

        //this will trigger in iteration 0 and inconsistent writer. the list will be empty!
        if (kbList.isEmpty())
            return;

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


        writer.append("signature\n");
        writer.append(kbList.get(0).getSignature().toString().toLowerCase());
        writer.append("\n\n");

        writer.append("\nconditionals\n");

        for (KnowledgeBase knowledgeBase : kbList) {
            writer.append("\n");
            writer.append(knowledgeBase.toFileString());

            iterationCounter++;
            totalCounter++;
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
