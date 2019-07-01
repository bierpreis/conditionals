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

    //todo
    @Override
    public void run() {
        while (running) {
            if (cpQueueToWrite.size() > maxNumberOfPairsInFile || flushRequested) {
                status = BufferStatus.WRITING;

                writeNextFile(cpQueueToWrite);
                flushRequested = false;
            } else if (requestedListNumber.get() != 0) {
                status = BufferStatus.READING;
                requestedList = readNextFile(requestedListNumber.get());
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
            PrintWriter writer = new PrintWriter(subFolder.toString() + "/" + fileName + ".txt", "UTF-8");

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

    //todo: read next file
    private Collection<AbstractPair> readPairs(int requestedK) {
        requestedListNumber.set(requestedK);
        while (!requestedListIsReady) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        requestedListIsReady = false;
        return requestedList;
    }

    //todo: dont read all files at once!
    private List<List<String>> getPairStringList(int requestedK) {


        List<List<String>> localFileStringListList = new ArrayList<>(filesArray.length);
        //if there are no files, there are no candidate pairs left so the empty list gets returned

        //todo: why always false?
        if (filesArray == null)
            return localFileStringListList;

        int currentFileNumber = 0;
        try {
            for (File file : filesArray) {
                Scanner fileScanner = new Scanner(file);

                StringBuilder sb = new StringBuilder();

                //todo: size this?
                localFileStringListList.add(new ArrayList<>());

                while (fileScanner.hasNextLine()) {
                    sb.append(fileScanner.nextLine());
                    sb.append("\n");
                }
                //todo: not all in one list but one list for every file
                localFileStringListList.get(currentFileNumber).add(sb.toString());
                currentFileNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //todo: these are the real pair strings?!
        List<String> pairStringList = new ArrayList<>();
        for (String fileString : localFileStringListList) {
            pairStringList.addAll(Arrays.asList(fileString.split("\nEND_PAIR\n\n")));
        }
        return localFileStringListList;
    }

    //todo: read next file
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
        requestedListIsReady = true;
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
        if (!queueToPrepare.isEmpty())
            return true;
        if (nextFileToReadNumber == filesList.size())
            return false;
        throw new RuntimeException("has more elements failed!");
    }


    @Override
    public AbstractPair getNextPair(int currentK) {
        if (queueToReturn.isEmpty()) {
            queueToReturn = queueToPrepare;
            queueToPrepare.clear();

        }

        return queueToReturn.poll();
    }


    @Override
    public boolean hasElementsForK(int requestedK) {
        return filesList.size() >= (requestedK - 1);
    }

    //todo
    @Override
    public void prepareCollection(int requestedK) {
        status = BufferStatus.PREPARING_NEXT_ITERATION;

        //todo: this list is not in order says javadoc. need to sort it?
        File folderToRead = new File(filePath + "/" + requestedK + "/");

        //todo: check if this takes too long
        filesList = Arrays.asList(folderToRead.listFiles());

        nextFileToReadNumber = 0;
        readCounter = 0;
        requestedListIsReady = false;

    }

    @Override
    public void addNewList(List<AbstractPair> listToAdd) {

        //todo what should this do?!
    }


    //todo: think about if this needs sync block
    @Override
    public void addPair(AbstractPair pairToAdd) {
        cpQueueToWrite.add(pairToAdd);

    }


}
