package kb_creator.model.buffer.hdd;

import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealPair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Pattern;

public class BufferReaderThread implements Runnable {

    private BlockingQueue<AbstractPair> lastIterationQueue;

    private int iterationNumberOfFiles;
    private int readingFileNameCounter;

    private volatile boolean hasMoreElements = true;

    private File folderToRead;
    private final Pattern END_PAIR_PATTERN = Pattern.compile("\nEND\n");
    private String tmpFilePath;
    private String numberOfDigitsString;


    public BufferReaderThread(BlockingQueue<AbstractPair> lastIterationQueue, String tmpFilePath, int requestedK, int numberOfDigits) {
        this.numberOfDigitsString = "%0" + numberOfDigits + "d";

        this.lastIterationQueue = lastIterationQueue;

        this.tmpFilePath = tmpFilePath;

        System.out.println("prepare iteration " + requestedK);
        readingFileNameCounter = 0;

        folderToRead = new File(tmpFilePath + "/" + (requestedK - 1) + "/");

        //todo: make this instance variable, sort and read this?
        File[] filesArray = folderToRead.listFiles();

        //files array is null when there are no files to read(this happens in iteration 0)
        if (filesArray != null) {
            System.out.println("number of files found for iteration " + requestedK + ": " + filesArray.length);
            iterationNumberOfFiles = filesArray.length;
        } else System.out.println("no files to read for iteration: " + requestedK);

    }

    @Override
    public void run() {
        while (readingFileNameCounter < iterationNumberOfFiles) {
            List<AbstractPair> pairsList = readNextFile();
            if (pairsList != null)
                for (AbstractPair pairToPut : pairsList)
                    try {
                        lastIterationQueue.put(pairToPut);
                    } catch (InterruptedException e) {

                        return; //this is ONLY triggered by stop button in gui and will close this thread
                    }
        }
        hasMoreElements = false;
    }


    private List<AbstractPair> readNextFile() {


        File fileToRead = new File(folderToRead + "/" + readingFileNameCounter + ".txt");
        System.out.println("reading file: " + fileToRead.getAbsolutePath());
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(fileToRead);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found in Hdd Buffer!");
        }

        StringBuilder sb = new StringBuilder();

        while (fileScanner.hasNextLine()) {
            sb.append(fileScanner.nextLine());
            sb.append("\n");
        }

        String[] fileStringArray = END_PAIR_PATTERN.split(sb.toString());
        List<AbstractPair> pairsList = new ArrayList<>(fileStringArray.length);

        for (String stringFromFile : fileStringArray) {
            pairsList.add(new RealPair(stringFromFile));
        }

        readingFileNameCounter++;
        return pairsList;
    }

    //iteration change methods

    public boolean hasMoreElements() {
        return hasMoreElements;
    }

    public void deleteOldData(int requestedK) {
        //don't delete files for iteration 0 because there wont be any
        File folderToDelete = new File(tmpFilePath + "/" + (requestedK - 1) + "/");

        if (folderToDelete.exists()) {
            for (File subFile : folderToDelete.listFiles())
                subFile.delete();

            folderToDelete.delete();

        }

    }

    //getters

    public int getQueueSize() {
        return lastIterationQueue.size();
    }

}
