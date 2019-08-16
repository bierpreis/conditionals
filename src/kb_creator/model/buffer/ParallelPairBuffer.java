package kb_creator.model.buffer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealListPair;
import kb_creator.model.propositional_logic.NewConditional;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelPairBuffer extends AbstractPairBuffer {

    private int iterationNumberOfFiles;
    private volatile boolean hasNextIteration;
    private int pairWriterCounter;

    //todo: check this class
    private Queue<AbstractPair> queueToReturn;
    //if queue to return is lower than this value, a new file will be read and the queue gets filled again
    private final int READ_QUEUE_MIN = 1000;


    public ParallelPairBuffer(String filePath, int maxNumberOfPairsInFile) {
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

        requestedListNumber = new AtomicInteger(0);


        status = BufferStatus.NOT_STARTED;

    }

    @Override
    public void run() {
        while (running) {
            if (cpQueueToWrite.size() > maxNumberOfPairsInFile || (flushRequested && cpQueueToWrite.size() > 0)) {
                status = BufferStatus.WRITING;
                writeNextFile(cpQueueToWrite);
            } else if (readingFileNameCounter < iterationNumberOfFiles) {
                if (queueToReturn.size() < READ_QUEUE_MIN) {//this value has pactically no impact on speed at all
                    status = BufferStatus.READING;
                    queueToReturn.addAll(readNextFile(requestedListNumber.get()));
                }
            } else {
                try {
                    //System.out.println("buffer sleeping");
                    status = BufferStatus.SLEEPING;
                    Thread.sleep(100); //this sleep also has practically no impact on speed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private void writeNextFile(Queue queueToWrite) {
        File subFolder;
        if (!queueToWrite.isEmpty()) {
            //todo: prepare iteration should be here. can save a lot of stuff
            subFolder = new File(tmpFilePath + "/" + ((AbstractPair) queueToWrite.peek()).getKnowledgeBase().getSize() + "/");//todo: maybe here too much get size?!
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
                    if (i != maxNumberOfPairsInFile - 1)
                        sb.append("\nEND_PAIR\n\n");
                    pairToWrite.clear();
                    pairWriterCounter++;

                }

                writer.print(sb.toString());
                writer.flush();
                writer.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<AbstractPair> readNextFile(int requestedK) {

        //read String
        File fileToRead = new File(tmpFilePath + "/" + requestedK + "/" + String.format("%05d", readingFileNameCounter) + ".txt");
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


        String[] fileStringArray = sb.toString().split("\nEND_PAIR\n\n");
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
    public void addPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidatesToAdd) {
        cpQueueToWrite.add(new RealListPair(knowledgeBase, candidatesToAdd));
    }

    @Override
    public void clear(int requestedK) {
        if (deleteFiles) {
            File folderToDelete = new File(tmpFilePath + "/" + (requestedK) + "/");

            if (folderToDelete.exists()) {
                for (File subFile : folderToDelete.listFiles())
                    subFile.delete();

                folderToDelete.delete();

            }
        }
    }


    @Override
    public boolean hasMoreElements(int currentK) {
        if (!queueToReturn.isEmpty())
            return true;

        return (readingFileNameCounter < iterationNumberOfFiles);
    }


    @Override
    public AbstractPair getNextPair(int currentK) {
        while (queueToReturn.peek() == null)
            try {
                System.out.println("buffer sleeping because queue is empty!");
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return queueToReturn.poll();
    }


    @Override
    public boolean hasElementsForK(int requestedK) {
        return hasNextIteration;
    }


    @Override
    protected void prepareIteration(int requestedK) {
        status = BufferStatus.PREPARING_NEXT_ITERATION;
        System.out.println("preparing iteration: " + requestedK);

        pairWriterCounter = 0;
        writingFileNameCounter = 0;

        File folderToRead = new File(tmpFilePath + "/" + (requestedK) + "/");

        hasNextIteration = folderToRead.exists();

        //if no next iteration exists, the steps here are not needed and would cause null pointer exeption because of the missing file
        if (!hasNextIteration)
            return;

        readingFileNameCounter = 0;
        requestedListNumber.set(requestedK);


        long beforeReadFiles = System.currentTimeMillis();

        File[] filesArray = folderToRead.listFiles();
        System.out.println("number of files found for " + requestedK + " iteration: " + filesArray.length);
        iterationNumberOfFiles = filesArray.length;
        Arrays.sort(filesArray);

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

        clear(requestedK);

        prepareIteration(requestedK + 1);
        System.out.println("finished iteration: " + requestedK);
    }

    public int getReaderBufferSize() {
        return queueToReturn.size();
    }

}
