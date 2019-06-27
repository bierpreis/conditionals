package kb_creator.model.cp_buffer;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.conditionals.pairs.CandidateNumbersArrayPair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class FasterSimpleBuffer extends AbstractBuffer {
    private final int maxNumberOfPairsInFile = 200;


    private AtomicInteger requestedKList;
    private List<AbstractPair> requestedList;
    private volatile boolean requestedListIsReady;

    private String folderToSavePath;
    private boolean running;
    private volatile boolean flushRequested;

    //todo: this thing hast to know if it has more elements or if all are used
    //idea: first read number of files
    //then read file for file and count files. this will show if more is left
    //then sth with iterator?! look if iterator and queue will hold ordering. else sth with lists.
    public FasterSimpleBuffer(String filePath) {
        writeCounter = 0;
        readCounter = 0;
        flushRequested = false;
        running = true;
        System.out.println("candidate pairs will be buffered on extra memory");
        if (filePath != null) {
            this.folderToSavePath = filePath + "/tmp/";

            File tmpFile = new File(this.folderToSavePath);
            tmpFile.mkdirs();
        }

        cpQueueToWrite = new ConcurrentLinkedQueue<AbstractPair>();
        requestedKList = new AtomicInteger(0);
        requestedListIsReady = false;
        status = BufferStatus.NOT_STARTED;

    }

    @Override
    public void run() {
        while (running) {
            if (cpQueueToWrite.size() > maxNumberOfPairsInFile || flushRequested) {
                status = BufferStatus.WRITING;

                writeAllPairs(cpQueueToWrite);
                flushRequested = false;
            } else if (requestedKList.get() != 0) {
                status = BufferStatus.READING;
                requestedList = readAllPairs(requestedKList.get());
                requestedKList.set(0);

            } else
                try {
                    status = BufferStatus.SLEEPING;
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }


    }

    @Override
    public void addCpToWrite(AbstractPair pairToAdd) {
        cpQueueToWrite.add(pairToAdd);
    }

    @Override
    public void addCpList(List<AbstractPair> listToAdd) {
        cpQueueToWrite.addAll(listToAdd);
    }


    private void writeAllPairs(Queue queueToWrite) {
        File subFolder = null;
        if (!queueToWrite.isEmpty()) {
            subFolder = new File(folderToSavePath + "/" + ((AbstractPair) queueToWrite.peek()).getKnowledgeBase().getSize() + "/");
            if (!subFolder.exists())
                subFolder.mkdirs();
        }

        try {

            while (!queueToWrite.isEmpty()) {
                //add leading zeros so the files will be soreted in correct order in their folder
                String fileName = String.format("%05d", writeCounter);
                writeCounter++;
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
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void deleteFiles(int numberOfConditionals) {
        System.out.println("trying to delete " + numberOfConditionals + " element pairs");
        File fileToDelete = new File(folderToSavePath + "/" + numberOfConditionals + "/");
        boolean fileDeletedSuccesfully;
        try {
            for (File file : fileToDelete.listFiles()) {
                if (!file.isDirectory()) {
                    if (!
                            file.delete()
                    )
                        System.out.println("deleting candidate pair file failed!");

                }

            }
        } catch (NullPointerException e) {
            System.out.println("no " + numberOfConditionals + " element pairs found for deleting");
        }
    }


    public int getQueueToWriteSize() {
        return cpQueueToWrite.size();
    }


    public void stop() {
        running = false;
    }


    public List<AbstractPair> getList(int requestedK) {

        return requestedList;
    }

    @Override
    public void prepareCollection(int requestedK) {
        //todo: here use readpairs and readallpairs?!
        readPairs(requestedK);
    }

    //todo: delete this? or make private?
    public List<AbstractPair> readPairs(int requestedK) {
        requestedKList.set(requestedK);
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

    //and: are this numbers correct?
    private List<AbstractPair> readAllPairs(int requestedK) {
        readCounter = 0;
        List<String> stringList = getPairStringList(requestedK);

        List<AbstractPair> pairsList = new ArrayList<>(stringList.size());

        for (String stringFromFile : stringList) {
            pairsList.add(new CandidateNumbersArrayPair(stringFromFile));
            readCounter++;
        }
        //todo: is this good here?
        requestedList = pairsList;


        requestedListIsReady = true;
        return pairsList;
    }

    private List<String> getPairStringList(int requestedK) {

        List<String> fileStringList = new ArrayList<>();
        //read String
        File fileToRead = new File(folderToSavePath + "/" + requestedK + "/");

        File[] filesArray = fileToRead.listFiles();

        //if there are no files, there are no candidate pairs left so the empty list gets returned
        if (filesArray == null)
            return fileStringList;


        try {
            for (File file : filesArray) {
                Scanner fileScanner = new Scanner(file);

                StringBuilder sb = new StringBuilder();

                while (fileScanner.hasNextLine()) {
                    sb.append(fileScanner.nextLine());
                    sb.append("\n");
                }

                fileStringList.add(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> pairStringList = new ArrayList<>();
        for (String fileString : fileStringList) {
            pairStringList.addAll(Arrays.asList(fileString.split("\nEND_PAIR\n\n")));
        }
        return pairStringList;
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
        writeCounter = 0;
    }

}
