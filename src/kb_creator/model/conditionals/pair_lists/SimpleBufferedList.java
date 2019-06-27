package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.conditionals.pairs.CandidateNumbersArrayPair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class SimpleBufferedList extends AbstractCandidateCollection {
    private int currentK;


    public SimpleBufferedList(String filePath) {
        super(filePath);
        System.out.println("created simple buffered list for candidate pairs");


        requestedListIsReady = false;
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

    //todo
    @Override
    public boolean hasMoreElements() {
        return false;
    }

    //todo
    @Override
    public AbstractPair getNextPair() {
        return null;
    }

    //todo
    @Override
    public boolean hasElementsForK(int requestedK) {
        return false;
    }

    @Override
    public void prepareCollection(int requestedK) {
        this.currentK = requestedK;
        flushWritingElements();

        //todo: read elements

    }

    @Override
    public void addNewList(int k, List<AbstractPair> listToAdd) {

        //todo

    }


    @Override
    public void addPair(AbstractPair pairToAdd) {
        cpQueueToWrite.add(pairToAdd);
    }


    private List<AbstractPair> readPairs(int requestedK) {
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
        File fileToRead = new File(filePath + "/" + requestedK + "/");

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

    private void writeAllPairs(Queue queueToWrite) {
        File subFolder = null;
        if (!queueToWrite.isEmpty()) {
            subFolder = new File(filePath + "/" + ((AbstractPair) queueToWrite.peek()).getKnowledgeBase().getSize() + "/");
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


}
