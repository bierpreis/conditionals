package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.conditionals.pairs.CandidateNumbersArrayPair;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelBufferedList extends AbstractCandidateCollection {
    private Queue<AbstractPair> queueToReturn;
    private Queue<AbstractPair> queueToPrepare;
    private List<File> filesList;
    private int nextFileToReadNumber;

    public ParallelBufferedList(String filePath) {
        super(filePath);
        System.out.println("created parallel list for candidate pairs");

        fileNameCounter = 0;
        readCounter = 0;

        queueToReturn = new LinkedBlockingQueue<>();
        queueToPrepare = new LinkedBlockingQueue<>();

        flushRequested = false;
        running = true;


        this.filePath = filePath + "/tmp/";

        File tmpFile = new File(this.filePath);
        tmpFile.mkdirs();


        cpQueueToWrite = new ConcurrentLinkedQueue<AbstractPair>();

        requestedListNumber = new AtomicInteger(0);


        status = BufferStatus.NOT_STARTED;

    }


    @Override
    public void run() {
        while (running) {
            if (cpQueueToWrite.size() > maxNumberOfPairsInFile || flushRequested) {
                status = BufferStatus.WRITING;
                if (cpQueueToWrite.size() > 0)
                    writeNextFile(cpQueueToWrite);
                flushRequested = false;
            } else if (requestedListNumber.get() != 0) {
                status = BufferStatus.READING;
                //todo: why put reading in requestedList and not in queue?
                queueToPrepare.addAll(readNextFile(requestedListNumber.get()));
                requestedListIsReady = true;
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
            subFolder = new File(filePath + "/" + ((AbstractPair) queueToWrite.peek()).getKnowledgeBase().getSize() + "/");
            if (!subFolder.exists())
                subFolder.mkdirs();
        }

        try {

            //add leading zeros so the files will be soreted in correct order in their folder
            String fileName = String.format("%05d", fileNameCounter);
            fileNameCounter++;
            if (subFolder == null)
                System.out.println("it was null!");
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
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<AbstractPair> readNextFile(int requestedK) {
        //read String
        File fileToRead = new File(filePath + "/" + requestedK + "/" + String.format("%05d", fileNameCounter));


        String[] fileStringArray = fileToRead.toString().split("\nEND_PAIR\n\n");

        List<AbstractPair> pairsList = new ArrayList<>(fileStringArray.length);

        for (String stringFromFile : fileStringArray) {
            pairsList.add(new CandidateNumbersArrayPair(stringFromFile));
            readCounter++;
        }


        nextFileToReadNumber++;

        return pairsList;
    }


    @Override
    public void flushWritingElements() {
        flushRequested = true;

        while (!cpQueueToWrite.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fileNameCounter = 0;
    }

    @Override
    public void clear(int requestedK) {
        //nothing
    }


    @Override
    public boolean hasMoreElements(int currentK) {

        if (!queueToReturn.isEmpty())
            return true;
        if (nextFileToReadNumber == filesList.size())
            return false;

        //if nothing from above triggered, reader thread is propably reading, so wait and check again later
        if (!requestedListIsReady)
            try {
                status = BufferStatus.SLEEPING;
                System.out.println("sleeping!!");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return hasMoreElements(currentK);
    }


    @Override
    public AbstractPair getNextPair(int currentK) {
        if (queueToReturn.isEmpty()) {
            queueToReturn = queueToPrepare;
            queueToPrepare = new LinkedBlockingQueue<>();

        }

        return queueToReturn.poll();
    }


    @Override
    public boolean hasElementsForK(int requestedK) {
        return filesList.size() >= (requestedK - 1);
    }

    @Override
    public void prepareCollection(int requestedK) {
        status = BufferStatus.PREPARING_NEXT_ITERATION;
        requestedListNumber.set(requestedK);
        File folderToRead = new File(filePath + "/" + requestedK + "/");

        long beforeReadFiles = System.currentTimeMillis();
        File[] filesArray = folderToRead.listFiles();
        Arrays.sort(filesArray);
        filesList = Arrays.asList(filesArray);
        System.out.println("reading folder took " + (System.currentTimeMillis() - beforeReadFiles) / 1000 + " seconds");

        nextFileToReadNumber = 0;
        readCounter = 0;
        requestedListIsReady = false;
    }

    @Override
    public void addNewList(List<AbstractPair> listToAdd) {
        flushWritingElements();
        cpQueueToWrite.addAll(listToAdd);
    }


    @Override
    public void addPair(AbstractPair pairToAdd) {
        cpQueueToWrite.add(pairToAdd);

    }


}
