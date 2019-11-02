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

    private int pairWriterCounter;

    private volatile boolean flushRequested = false;

    private BlockingQueue<AbstractPair> cpQueueToWrite = new ArrayBlockingQueue<>(10_000);

    private final Object FLUSH_WAIT_OBJECT = new Object();

    private volatile boolean running = true;

    private int requestedK;

    private String numberOfDigitsString;


    public BufferWriterThread(String tmpFilePath, int maxNumberOfPairsInFile, int requestedK, int fileNameDigits) {
        numberOfDigitsString = "%0" + fileNameDigits + "d";
        this.requestedK = requestedK;

        this.maxNumberOfPairsInFile = maxNumberOfPairsInFile;

        File tmpFile = new File(tmpFilePath);
        tmpFile.mkdirs();


        pairWriterCounter = 0;
        writingFileNameCounter = 0;


        folderToWrite = new File(tmpFilePath + "/" + requestedK + "/");
        folderToWrite.mkdirs();
    }

    @Override
    public void run() {
        System.out.println("buffer writer thread started for k " + requestedK);
        while (running) {
            if (checkIfShouldWrite()) {
                writeNextFile();
            } else try {
                Thread.sleep(50); //50 seems to be a good value. lower or higher values only change a little bit
            } catch (InterruptedException e) {
                return; //this is triggered by stop button in gui
            }

        }
        System.out.println("buffer writer thread finished for k " + requestedK);
    }

    public boolean checkIfShouldWrite() {
        return (cpQueueToWrite.size() > maxNumberOfPairsInFile || (flushRequested && cpQueueToWrite.size() > 0));
    }

    private void writeNextFile() {
        try {
            //add leading zeros so the files can be sorted in correct order in their folder
            String fileName = String.format(numberOfDigitsString, writingFileNameCounter);
            writingFileNameCounter++;

            PrintWriter writer = new PrintWriter(folderToWrite.getAbsolutePath() + "/" + fileName + ".txt", "UTF-8");

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < maxNumberOfPairsInFile && !cpQueueToWrite.isEmpty(); i++) {
                AbstractPair pairToWrite;
                try {
                    pairToWrite = cpQueueToWrite.take();
                } catch (InterruptedException e) {
                    return; //can be triggered by gui top button
                }
                sb.append(pairToWrite.toFileString());
                if (i != maxNumberOfPairsInFile - 1)
                    sb.append("\nEND\n");
                pairToWrite.clear();
                pairWriterCounter++;
            }

            writer.print(sb.toString());
            writer.flush();
            writer.close();

            if (flushRequested && cpQueueToWrite.isEmpty())
                synchronized (FLUSH_WAIT_OBJECT) {
                    FLUSH_WAIT_OBJECT.notify();
                }


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



    public void finishIteration() {
        flushWritingElements();

    }

    public boolean hasNextIteration() {
        return (folderToWrite.listFiles().length > 0);
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

    //getters

    public int getPairWriterCounter() {
        return pairWriterCounter;
    }

    public int getQueueSize() {
        return cpQueueToWrite.size();
    }
}
