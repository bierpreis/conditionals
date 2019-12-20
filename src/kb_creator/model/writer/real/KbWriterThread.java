package kb_creator.model.writer.real;

import kb_creator.model.logic.KnowledgeBase;
import kb_creator.model.writer.KbWriterOptions;

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

    private int maxKbInFile;

    private boolean numberFormat;


    public KbWriterThread(String subFolderName, BlockingQueue<KnowledgeBase> queue, KbWriterOptions writerOptions) {
        this.subFolderName = subFolderName;
        this.queue = queue;
        this.rootFilePath = writerOptions.getFilePath();
        this.numberOfDigitsString = "%0" + writerOptions.getFileNameLength() + "d";
        this.maxKbInFile = writerOptions.getRequestedKbNumber();
        this.numberFormat = writerOptions.isNumbersActive();

    }


    @Override
    public void run() {
        System.out.println("new writer thread started for " + subFolderName + " kbs");
        while (running) {
            List<KnowledgeBase> kbList = new ArrayList<>(maxKbInFile);

            int counter = 0;
            while (counter < maxKbInFile) {

                try {
                    kbList.add(queue.take());
                    counter++;
                } catch (InterruptedException e) {
                    //this triggers when iteration is finished and thread gets interrupted
                    break;
                }
            }
            writeKbListToFile(kbList);
        }
        System.out.println("writer thread closed for " + subFolderName + " kbs");
    }


    public void finishIteration() {

        //this will make the calling thread (creator) to wait until queue has been written
        while (!queue.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

                return; //this should only happen by gui stop button.
            }
        }
    }

    public void newIteration(int k) {


        //this triggered once 18.12. once maybe stop was pressed?
        if (!queue.isEmpty())
            throw new RuntimeException("new iteration when queue is not empty! last iteration did not finish correctly!");
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
            writer = new PrintWriter(currentIterationFilePath + kbList.get(0).getNamePrefix() + String.format(numberOfDigitsString, kbList.get(0).getNumber()) + ".txt", "UTF-8");

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

        //this should be a bit faster then incrementing counters in loop.
        iterationCounter = iterationCounter + kbList.size();
        totalCounter = totalCounter + kbList.size();


        if (numberFormat) {
            for (KnowledgeBase knowledgeBase : kbList) {
                writer.append("\n");
                writer.append(knowledgeBase.toNumbersFileString());
            }
        } else
            for (KnowledgeBase knowledgeBase : kbList) {
                writer.append("\n");
                writer.append(knowledgeBase.toStandardFileString());
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
