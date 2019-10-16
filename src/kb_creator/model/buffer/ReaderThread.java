package kb_creator.model.buffer;

import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealListPair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Pattern;

public class ReaderThread implements Runnable {

    private BlockingQueue<AbstractPair> queueToReturn;

    private int iterationNumberOfFiles;

    private int readingFileNameCounter;
    private File folderToRead;

    private final Pattern END_PAIR_PATTERN = Pattern.compile("\nEND\n");

    private String tmpFilePath;

    public ReaderThread(String tmpFilePath, int requestedK) {
        this.queueToReturn = new ArrayBlockingQueue<>(5_000); //todo
        this.tmpFilePath = tmpFilePath;

        System.out.println("prepare iteration " + requestedK);
        readingFileNameCounter = 0;

        //for iteration 0 there are no files to read

        folderToRead = new File(tmpFilePath + "/" + (requestedK - 1) + "/");
        File[] filesArray = folderToRead.listFiles();

        //files array is null when there are no files to read(this happens in iteration 0)
        if (filesArray != null) {
            System.out.println("number of files found for " + requestedK + " iteration: " + filesArray.length);
            iterationNumberOfFiles = filesArray.length;
        } else System.out.println("no files to read for iteration: " + requestedK);

    }

    @Override
    public void run() {
        System.out.println("buffer reader thread started for number of files: " + iterationNumberOfFiles);
        while (readingFileNameCounter < iterationNumberOfFiles ) { //todo: start this thread for every iteration and close if all files are read.
            List<AbstractPair> pairsList = readNextFile();
            if (pairsList != null)
                for (AbstractPair pairToPut : pairsList)
                    try {
                        queueToReturn.put(pairToPut);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
        }
        System.out.println("buffer reader thread closed. queue size: " + queueToReturn.size());
    }

    private boolean hasMoreFilesToRead() {
        return true;
    }


    private List<AbstractPair> readNextFile() {
        //read String
        //todo: %5d is not great.
        File fileToRead = new File(folderToRead + "/" + String.format("%05d", readingFileNameCounter) + ".txt");
        System.out.println("reading file: " + fileToRead.getAbsolutePath());
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(fileToRead);
        } catch (FileNotFoundException e) {
            return null;
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
        }

        readingFileNameCounter++;
        return pairsList;
    }

    public boolean hasMoreElementsForK(int k) {

        if (!queueToReturn.isEmpty())
            return true;

        return (readingFileNameCounter < iterationNumberOfFiles);
    }

    public AbstractPair getNextPair(int k) {
        try {
            return queueToReturn.take();
        } catch (InterruptedException e) {
            //intentionally nothing. when interrupted, the thread is supposed to shut down.
        }
        return null;
    }

    public int getQueueSize() {
        return queueToReturn.size();
    }

    //todo: rename?
    public void clear(int requestedK) {
        //dont delete files for iteration 0 because there wont be any
        File folderToDelete = new File(tmpFilePath + "/" + (requestedK - 1) + "/");

        if (folderToDelete.exists()) {
            for (File subFile : folderToDelete.listFiles())
                subFile.delete();

            folderToDelete.delete();

        }

    }

    public void prepareIteration(int requestedK) {

    }
}
