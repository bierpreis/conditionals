package kb_creator.model.buffer;

import kb_creator.model.pairs.AbstractPair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BufferWriterThread implements Runnable {

    private File folderToWrite;
    private int maxNumberOfPairsInFile;
    private int writingFileNameCounter;
    private String tmpFilePath;

    private int pairWriterCounter;

    private volatile boolean flushRequested = false;

    private BlockingQueue<AbstractPair> cpQueueToWrite = new ArrayBlockingQueue<>(5_000);

    private final Object FLUSH_WAIT_OBJECT = new Object();

    private volatile boolean running = true;

    private boolean deleteFiles;

    private int requestedK;

    private String numberOfDigitsString;


    public BufferWriterThread(String tmpFilePath, int maxNumberOfPairsInFile, int requestedK, boolean deleteFiles, int fileNameDigits) {
        numberOfDigitsString = "%0" + fileNameDigits + "d";
        this.deleteFiles = deleteFiles;
        this.requestedK = requestedK;
        System.out.println("set buffer size to " + maxNumberOfPairsInFile);

        this.maxNumberOfPairsInFile = maxNumberOfPairsInFile;

        this.tmpFilePath = tmpFilePath;

        File tmpFile = new File(this.tmpFilePath);
        tmpFile.mkdirs();


        pairWriterCounter = 0;
        writingFileNameCounter = 0;


        folderToWrite = new File(this.tmpFilePath + "/" + requestedK + "/");
        folderToWrite.mkdirs();
    }

    @Override
    public void run() {
        System.out.println("buffer writer thread started for k " + requestedK);
        while (running) {
            if (checkIfShouldWrite())
                writeNextFile(cpQueueToWrite);
            else try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return; //this is triggered by stop button in gui
            }

        }
        System.out.println("buffer writer thread finished for k " + requestedK);
    }

    public boolean checkIfShouldWrite() {
        return (cpQueueToWrite.size() > maxNumberOfPairsInFile || (flushRequested && cpQueueToWrite.size() > 0));
    }

    private void writeNextFile(Queue queueToWrite) {
        try {
            //add leading zeros so the files can be sorted in correct order in their folder
            String fileName = String.format(numberOfDigitsString, writingFileNameCounter);
            writingFileNameCounter++;

            PrintWriter writer = new PrintWriter(folderToWrite.getAbsolutePath() + "/" + fileName + ".txt", "UTF-8");

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < maxNumberOfPairsInFile && !queueToWrite.isEmpty(); i++) {

                AbstractPair pairToWrite = (AbstractPair) queueToWrite.poll();
                sb.append(pairToWrite.toFileString());
                if (i != maxNumberOfPairsInFile - 1)
                    sb.append("\nEND\n");
                pairToWrite.clear();
                pairWriterCounter++;
            }

            if (flushRequested && queueToWrite.isEmpty())
                synchronized (FLUSH_WAIT_OBJECT) {
                    FLUSH_WAIT_OBJECT.notify();
                }

            writer.print(sb.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void flushWritingElements() {
        System.out.println("flushing " + cpQueueToWrite.size() + " elements");

        flushRequested = true;


        //this wait causes the calling thread to wait until all pairs are written, then the calling thread can continue
        long timeBeforeWaiting = System.currentTimeMillis();
        while (!cpQueueToWrite.isEmpty()) {
            try {
                synchronized (FLUSH_WAIT_OBJECT) {
                    FLUSH_WAIT_OBJECT.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        flushRequested = false;
        System.out.println("flushing finished in " + (System.currentTimeMillis() - timeBeforeWaiting) + "ms");
        writingFileNameCounter = 0;
    }

    public int getQueueSize() {
        return cpQueueToWrite.size();
    }

    public void finishIteration(int requestedK) {
        flushWritingElements();

    }

    public boolean hasNextIteration() {
        return (folderToWrite.listFiles().length > 0);
    }

    public int getPairWriterCounter() {
        return pairWriterCounter;
    }

    public void addPair(AbstractPair pairToAdd) {
        try {
            cpQueueToWrite.put(pairToAdd);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addList(List<AbstractPair> listToAdd) {
        for (AbstractPair pair : listToAdd)
            try {
                cpQueueToWrite.put(pair);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public void stopLoop() {
        running = false;
    }
}
