package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.conditionals.pairs.CandidateNumbersArrayPair;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
            if (cpQueueToWrite.size() > maxNumberOfPairsInFile) {
                status = BufferStatus.WRITING;
                if (cpQueueToWrite.size() > 0)
                    writeNextFile(cpQueueToWrite);
            } else if (flushRequested) {
                status = BufferStatus.WRITING;
                writeNextFile(cpQueueToWrite);
                flushRequested = false;
            } else if (requestedListNumber.get() != 0) {
                status = BufferStatus.READING;
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
        System.out.println("writing " + queueToWrite.size() + " elements");
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

        System.out.println("writing finished" + queueToWrite.size());

    }

    private List<AbstractPair> readNextFile(int requestedK) {
        //read String
        File fileToRead = new File(filePath + "/" + requestedK + "/" + String.format("%05d", fileNameCounter) + ".txt");
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

        //todo: fileStringArray contains the filepath of file not the string which is in it
        for (String stringFromFile : fileStringArray) {
            pairsList.add(new CandidateNumbersArrayPair(stringFromFile));
            readCounter++;
        }


        nextFileToReadNumber++;

        return pairsList;
    }


    @Override
    public void flushWritingElements() {
        System.out.println("flushing");
        flushRequested = true;

        while (!cpQueueToWrite.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("flushing finished");
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
        System.out.println("preparing");
        flushWritingElements();
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
