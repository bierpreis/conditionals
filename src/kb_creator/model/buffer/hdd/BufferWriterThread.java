package kb_creator.model.buffer.hdd;

import kb_creator.model.logic.KnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealPair;

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

    private volatile int pairWriterCounter;

    private volatile boolean flushRequested = false;

    private BlockingQueue<AbstractPair> cpQueueToWrite;

    private final Object FLUSH_WAIT_OBJECT = new Object();

    private volatile boolean running = true;

    private BlockingQueue<KnowledgeBase> consistentQueue;


    public BufferWriterThread(BlockingQueue<AbstractPair> queueToWrite, BlockingQueue<KnowledgeBase> consistentQueue, String tmpFilePath, int maxNumberOfPairsInFile, int requestedK) {
        this.cpQueueToWrite = queueToWrite;
        this.consistentQueue = consistentQueue;

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
        while (running) {
            if (checkIfShouldWrite()) { //this causes jumping speed. but changing is is complicated.
                writeNextFile();
            } else try {

                Thread.sleep(50); //50 seems to be a good value. lower or higher values only change a little bit
            } catch (InterruptedException e) {
                return; //this is triggered by stop button in gui
            }
        }
    }

    public boolean checkIfShouldWrite() {
        return (cpQueueToWrite.size() > maxNumberOfPairsInFile || (flushRequested && cpQueueToWrite.size() > 0));
    }

    private void writeNextFile() {
        try {
            //add leading zeros so the files can be sorted in correct order in their folder


            //this triggered. this happens when too much files?
            //java.io.FileNotFoundException: /home/bierpreis/KBs/tmp/1/3945481.txt (Auf dem Gerät ist kein Speicherplatz mehr verfügbar)
            //with buffering when hdd was full. close program in this case?
            PrintWriter writer = new PrintWriter(folderToWrite.getAbsolutePath() + "/" + writingFileNameCounter + ".txt", "UTF-8");

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < maxNumberOfPairsInFile && !cpQueueToWrite.isEmpty(); i++) {
                AbstractPair pairToWrite;
                try {
                    pairToWrite = cpQueueToWrite.take();
                } catch (InterruptedException e) {
                    System.out.println("buffer writer thread interrupted by gui.");
                    return; //can be triggered by gui top button
                }

                try {
                    consistentQueue.put(pairToWrite.getKnowledgeBase());
                } catch (InterruptedException e) {
                    System.out.println("buffer writer thread inrerrupted by gui.");
                    return; //can be triggered by gui top button
                }

                sb.append(pairToWrite.toFileString());
                if (i != maxNumberOfPairsInFile - 1)
                    sb.append("\nEND\n");
                pairWriterCounter++;
            }

            writer.print(sb.toString());
            writer.flush();
            writer.close();

            writingFileNameCounter++;

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
                //this can happen when stop is pressed. should never happen
                System.out.println("wait in buffer writer interrupted. this should only happen when stop was pressed!");
                return;
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
