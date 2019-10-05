package kb_creator.model.buffer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealListPair;
import kb_creator.model.propositional_logic.NewConditional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;

public class ConcurrentPairBuffer extends AbstractPairBuffer {

    private int iterationNumberOfFiles;
    private volatile boolean hasNextIteration;
    private int pairWriterCounter;

    private final Object FLUSH_WAIT_OBJECT = new Object();
    private final Object THREAD_WAIT_OBJECT = new Object();

    private Queue<AbstractPair> queueToReturn;
    private Queue<AbstractPair> cpQueueToWrite;

    private final Pattern END_PAIR_PATTERN = Pattern.compile("\nEND\n");

    private volatile boolean flushRequested;

    //if queue to return is lower than this value, a new file will be read and the queue gets filled again
    //this value has almost no impact on speed at all
    private final int READ_QUEUE_MIN = 2000;

    private File folderToWrite;
    private File folderToRead;

    private int writingFileNameCounter;
    private int readingFileNameCounter;


    public ConcurrentPairBuffer(String filePath, int maxNumberOfPairsInFile) {
        super(filePath);
        System.out.println("created parallel buffer for candidate pairs");
        this.maxNumberOfPairsInFile = maxNumberOfPairsInFile;

        System.out.println("set buffer size to " + maxNumberOfPairsInFile);

        writingFileNameCounter = 0;

        queueToReturn = new ConcurrentLinkedQueue<>();

        cpQueueToWrite = new ConcurrentLinkedQueue<>();

        flushRequested = false;
        running = true;

        File tmpFile = new File(this.tmpFilePath);
        tmpFile.mkdirs();

        status = BufferStatus.NOT_STARTED;

    }

    @Override
    public void run() {
        while (running) {
            //writing has first priority
            if (checkIfShouldWrite()) {
                status = BufferStatus.WRITING;
                writeNextFile(cpQueueToWrite);
                //reading has second priority
            } else if (checkIfShouldRead()) {
                status = BufferStatus.READING;
                queueToReturn.addAll(readNextFile());

                //sleep if no writing or reading is needed
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


    public boolean checkIfShouldRead() {
        return (readingFileNameCounter < iterationNumberOfFiles && queueToReturn.size() < READ_QUEUE_MIN);
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

    private List<AbstractPair> readNextFile() {

        //read String
        File fileToRead = new File(folderToRead + "/" + String.format("%05d", readingFileNameCounter) + ".txt");
        System.out.println("reading file: " + fileToRead.getAbsolutePath());
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(fileToRead);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();

        while (fileScanner.hasNextLine()) {
            sb.append(fileScanner.nextLine());
            sb.append("\n");
        }

        String[] fileStringArray = END_PAIR_PATTERN.split(sb.toString());
        List<AbstractPair> pairsList = new ArrayList<>(fileStringArray.length);

        for (String stringFromFile : fileStringArray) {
            pairsList.add(new RealListPair(stringFromFile));
            pairReaderCounter++;
        }

        readingFileNameCounter++;
        return pairsList;
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
        cpQueueToWrite.add(new RealListPair(knowledgeBase, candidatesToAdd));
    }

    @Override
    public void addPair(AbstractPair pair) {
        cpQueueToWrite.add(pair);
    }

    @Override
    public void clear(int requestedK) {
        //dont delete files for iteration 0 because there wont be any
        if (deleteFiles && requestedK != 0) {
            File folderToDelete = new File(tmpFilePath + "/" + (requestedK - 1) + "/");

            if (folderToDelete.exists()) {
                for (File subFile : folderToDelete.listFiles())
                    subFile.delete();

                folderToDelete.delete();

            }
        }
    }


    @Override
    public boolean hasMoreElements(int k) {
        if (!queueToReturn.isEmpty())
            return true;

        return (readingFileNameCounter < iterationNumberOfFiles);
    }


    @Override
    public AbstractPair getNextPair(int k) {
        while (queueToReturn.peek() == null)
            try {
                System.out.println("main thread waiting for buffer ...");
                Thread.sleep(50); //sleep is shit so fix it if this class should be needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return queueToReturn.poll();
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


        readingFileNameCounter = 0;

        //for iteration 0 there are no files to read
        if (requestedK != 0) {
            folderToRead = new File(tmpFilePath + "/" + (requestedK - 1) + "/");
            File[] filesArray = folderToRead.listFiles();
            System.out.println("number of files found for " + requestedK + " iteration: " + filesArray.length);
            iterationNumberOfFiles = filesArray.length;

        }

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

        clear(requestedK);

        System.out.println("finished iteration: " + requestedK);
    }

    @Override
    public int getReaderBufferSize() {
        return queueToReturn.size();
    }

}
