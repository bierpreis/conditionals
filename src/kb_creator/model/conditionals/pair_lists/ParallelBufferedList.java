package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.conditionals.pairs.CandidateNumbersArrayPair;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelBufferedList extends AbstractCandidateCollection {
    private Queue<AbstractPair> queueToReturn;
    private Queue<AbstractPair> queueToPrepare;
    private int iterationNumberOfFiles;
    private List<File> filesList;
    private volatile boolean hasNextIteration;
    private volatile int lastIterationPairAmount;

    public ParallelBufferedList(String filePath) {
        super(filePath);
        System.out.println("created parallel list for candidate pairs");

        writingFileNameCounter = 0;
        pairReaderCounter = 0;

        queueToReturn = new LinkedBlockingQueue<>();
        queueToPrepare = new LinkedBlockingQueue<>();

        flushRequested = false;
        running = true;

        File tmpFile = new File(this.tmpFilePath);
        tmpFile.mkdirs();


        cpQueueToWrite = new ConcurrentLinkedQueue<AbstractPair>();

        requestedListNumber = new AtomicInteger(0);


        status = BufferStatus.NOT_STARTED;

    }


    @Override
    public void run() {
        while (running) {
            if (cpQueueToWrite.size() > maxNumberOfPairsInFile) {
                status = BufferStatus.WRITING;
                writeNextFile(cpQueueToWrite);
            } else if (flushRequested) {
                status = BufferStatus.WRITING;
                if (cpQueueToWrite.size() > 0)
                    writeNextFile(cpQueueToWrite);

            } else if (queueToPrepare.isEmpty() && requestedListNumber.get() != 0) {

                status = BufferStatus.READING;
                queueToPrepare.addAll(readNextFile(requestedListNumber.get()));

                requestedListNumber.set(0);
            } else
                try {
                    status = BufferStatus.SLEEPING;
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }


    }

    private void writeNextFile(Queue queueToWrite) {
        File subFolder = null;
        if (!queueToWrite.isEmpty()) {
            subFolder = new File(tmpFilePath + "/" + ((AbstractPair) queueToWrite.peek()).getKnowledgeBase().getSize() + "/");
            if (!subFolder.exists())
                subFolder.mkdirs();


            try {

                //add leading zeros so the files will be soreted in correct order in their folder
                String fileName = String.format("%05d", writingFileNameCounter);
                writingFileNameCounter++;


                PrintWriter writer = new PrintWriter(subFolder.getAbsolutePath() + "/" + fileName + ".txt", "UTF-8");

                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < maxNumberOfPairsInFile && !queueToWrite.isEmpty(); i++) {

                    AbstractPair pairToWrite = (AbstractPair) queueToWrite.poll();
                    sb.append(pairToWrite.toFileString());
                    sb.append("\nEND_PAIR\n\n");
                    pairToWrite.deleteCandidates();
                    pairToWrite.deleteKB();

                }

                writer.print(sb.toString().replaceAll("\nEND_PAIR\n\n$", ""));
                writer.flush();
                writer.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<AbstractPair> readNextFile(int requestedK) {
        System.out.println("reading file for k: " + requestedK);
        //read String
        File fileToRead = new File(tmpFilePath + "/" + requestedK + "/" + String.format("%05d", readingFileNameCounter) + ".txt");
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


        String[] fileStringArray = sb.toString().split("\nEND_PAIR\n\n");
        List<AbstractPair> pairsList = new ArrayList<>(fileStringArray.length);

        for (String stringFromFile : fileStringArray) {
            pairsList.add(new CandidateNumbersArrayPair(stringFromFile));
            pairReaderCounter++;
        }


        readingFileNameCounter++;

        return pairsList;
    }


    @Override
    public void flushWritingElements() {
        System.out.println("flushing " + cpQueueToWrite.size() + " elements");

        flushRequested = true;

        long timeBeforeWaiting = System.currentTimeMillis();
        while (!cpQueueToWrite.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        flushRequested = false;
        System.out.println("flushing finished in " + (System.currentTimeMillis() - timeBeforeWaiting) + "ms");
        writingFileNameCounter = 0;
    }

    @Override
    public void clear(int requestedK) {
        //nothing
    }

    //todo: this is not good at all
    @Override
    public boolean hasMoreElements(int currentK) {


        while (flushRequested)
            try {
                System.out.println("sleeping");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        //todo: this is shit. if the dangerous stuff triggered, and both queues are at some point 0, every input which can come later will be ignored
        if (queueToReturn.isEmpty() && !queueToPrepare.isEmpty()) {
            System.out.println("!!!!DANGEROUS SUFF TRIGGERED!!!!");
            queueToReturn = queueToPrepare;
            queueToPrepare = new LinkedBlockingQueue<>();
        }
        boolean returnValue = !queueToReturn.isEmpty();

        return returnValue;
    }

    //todo: this sucks
    @Override
    public AbstractPair getNextPair(int currentK) {
        if (queueToReturn.isEmpty()) {
            System.out.println("switched empty queue to return for queue to prepare with " + queueToPrepare.size() + "elements");
            queueToReturn = queueToPrepare;
            queueToPrepare = new LinkedBlockingQueue<>();

        }

        return queueToReturn.poll();
    }


    @Override
    public boolean hasElementsForK(int requestedK) {
        return hasNextIteration;
    }


    @Override
    public void prepareIteration(int requestedK) {
        status = BufferStatus.PREPARING_NEXT_ITERATION;
        System.out.println("preparing iteration: " + requestedK);

        writingFileNameCounter = 0;
        readingFileNameCounter = 0;
        lastIterationPairAmount = pairReaderCounter;
        pairReaderCounter = 0;


        File folderToRead = new File(tmpFilePath + "/" + requestedK + "/");

        hasNextIteration = folderToRead.exists();

        //if no next iteration exists, the steps here are not needed and would cause null pointer exeption because of the missing file
        if (!hasNextIteration)
            return;

        requestedListNumber.set(requestedK);


        long beforeReadFiles = System.currentTimeMillis();

        //todo: this is shit, this shows wait in finish iteration doenst work
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        File[] filesArray = folderToRead.listFiles();
        System.out.println("number of files found for " + requestedK + " iteration: " + filesArray.length);
        iterationNumberOfFiles = filesArray.length;
        Arrays.sort(filesArray);
        filesList = Arrays.asList(filesArray);
        System.out.println("reading folder took " + (System.currentTimeMillis() - beforeReadFiles) / 1000 + " seconds");

    }

    @Override
    public void addNewList(List<AbstractPair> listToAdd) {
        cpQueueToWrite.addAll(listToAdd);
    }


    @Override
    public void addPair(AbstractPair pairToAdd) {
        cpQueueToWrite.add(pairToAdd);

    }

    @Override
    public void finishIteration(int requestedK) {
        System.out.println("finishing iteration: " + requestedK);
        status = BufferStatus.FINISHING_ITERATION;
        flushWritingElements();
    }


}
