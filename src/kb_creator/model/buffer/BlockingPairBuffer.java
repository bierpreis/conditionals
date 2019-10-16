package kb_creator.model.buffer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealListPair;
import kb_creator.model.propositional_logic.NewConditional;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class BlockingPairBuffer extends AbstractPairBuffer {

    private volatile boolean hasNextIteration;
    private int pairWriterCounter;

    private final Object FLUSH_WAIT_OBJECT = new Object();
    private final Object THREAD_WAIT_OBJECT = new Object();

    private ReaderThread readerThreadObject;
    private Thread readerThread; //todo: close reader thread with stop button

    private BlockingQueue<AbstractPair> cpQueueToWrite;

    private final Pattern END_PAIR_PATTERN = Pattern.compile("\nEND\n");

    private volatile boolean flushRequested;

    //if queue to return is lower than this value, a new file will be read and the queue gets filled again
    //this value has almost no impact on speed at all
    private final int READ_QUEUE_MIN = 2000;

    private File folderToWrite;


    private int writingFileNameCounter;


    public BlockingPairBuffer(String filePath, int maxNumberOfPairsInFile) {
        super(filePath);
        System.out.println("created parallel buffer for candidate pairs");
        this.maxNumberOfPairsInFile = maxNumberOfPairsInFile;

        System.out.println("set buffer size to " + maxNumberOfPairsInFile);

        writingFileNameCounter = 0;


        cpQueueToWrite = new ArrayBlockingQueue<>(10_000);

        flushRequested = false;
        running = true;

        File tmpFile = new File(this.tmpFilePath);
        tmpFile.mkdirs();

        status = BufferStatus.NOT_STARTED;

    }

    //todo: remove this
    @Override
    public void run() {
        while (running) {
            //writing has first priority
            if (checkIfShouldWrite()) {
                status = BufferStatus.WRITING;
                writeNextFile(cpQueueToWrite);
                //reading has second priority
            } else {
                try {
                    status = BufferStatus.SLEEPING;
                    synchronized (THREAD_WAIT_OBJECT) {
                        THREAD_WAIT_OBJECT.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean checkIfShouldWrite() {
        return (cpQueueToWrite.size() > maxNumberOfPairsInFile || (flushRequested && cpQueueToWrite.size() > 0));
    }


    @Override
    public void notifyBuffer() {
        synchronized (THREAD_WAIT_OBJECT) {
            THREAD_WAIT_OBJECT.notify();
        }
    }

    private void writeNextFile(Queue queueToWrite) {
        try {
            //add leading zeros so the files can be sorted in correct order in their folder
            String fileName = String.format("%05d", writingFileNameCounter);
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

    @Override
    public void addPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidatesToAdd) {
        try {
            cpQueueToWrite.put(new RealListPair(knowledgeBase, candidatesToAdd));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPair(AbstractPair pair) {
        try {
            cpQueueToWrite.put(pair);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOldData(int requestedK) {
        if (deleteFiles && requestedK != 0) {
            readerThreadObject.deleteOldData(requestedK);
        }
    }

    @Override
    public int getQueueToWriteSize() {
        return cpQueueToWrite.size();
    }


    @Override
    public boolean hasMoreElementsForK(int k) {

        return readerThreadObject.hasMoreElementsForK(k);
    }

    @Override
    public AbstractPair getNextPair(int k) {
        return readerThreadObject.getNextPair(k);
    }


    @Override
    public boolean hasElementsForNextK(int k) {
        return hasNextIteration;
    }

    @Override
    public void prepareIteration(int requestedK) {
        status = BufferStatus.PREPARING_NEXT_ITERATION;
        System.out.println("preparing iteration: " + requestedK);

        pairWriterCounter = 0;
        writingFileNameCounter = 0;


        folderToWrite = new File(tmpFilePath + "/" + requestedK + "/");
        folderToWrite.mkdirs();

        readerThreadObject = new ReaderThread(tmpFilePath, requestedK);
        Thread readerThread = new Thread(readerThreadObject);
        readerThread.setName("reader thread for k " + requestedK);
        readerThread.start();


        System.out.println("prepare iteration finished " + requestedK);

    }

    @Override
    public void addNewList(List<AbstractPair> listToAdd) {
        cpQueueToWrite.addAll(listToAdd);
    }


    @Override
    public void finishIteration(int requestedK) {

        status = BufferStatus.FINISHING_ITERATION;

        flushWritingElements();
        lastIterationPairAmount = pairWriterCounter;

        hasNextIteration = (folderToWrite.listFiles().length > 0);

        deleteOldData(requestedK);

        System.out.println("finished iteration: " + requestedK);
    }

    @Override
    public int getReaderBufferSize() {
        return readerThreadObject.getQueueSize();
    }

}
