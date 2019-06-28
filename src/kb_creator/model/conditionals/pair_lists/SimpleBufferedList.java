package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.conditionals.pairs.CandidateNumbersArrayPair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleBufferedList extends AbstractCandidateCollection {

    private List<List<AbstractPair>> pairsListList;
    private int nextElementNumberToReturn;


    public SimpleBufferedList(String filePath) {
        super(filePath);
        System.out.println("created simple buffered list for candidate pairs");

        requestedKList = new AtomicInteger(0);
        requestedListIsReady = false;
        pairsListList = new ArrayList<>();
        nextElementNumberToReturn = 0;
        cpQueueToWrite = new ConcurrentLinkedQueue<>();
        running = true;
    }


    @Override
    public void run() {
        while (running) {
            if (cpQueueToWrite.size() > maxNumberOfPairsInFile || flushRequested) {
                status = BufferStatus.WRITING;
                writeAllPairs(cpQueueToWrite);
                flushRequested = false;
            } else if (requestedKList.get() != 0) {
                System.out.println("reading: " + requestedKList.get());
                status = BufferStatus.READING;

                //remove the "null" pairs with empty kb and candidates
                pairsListList.get(requestedKList.get() - 1).clear();

                //add candidates from file
                pairsListList.get(requestedKList.get() - 1).addAll(readAllPairs(requestedKList.get()));

                requestedKList.set(0);
                System.out.println("finished reading");

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
    public boolean hasMoreElements(int currentK) {
        return pairsListList.get(currentK).size() > nextElementNumberToReturn;
    }


    @Override
    public AbstractPair getNextPair(int currentK) {
        nextElementNumberToReturn++;
        return pairsListList.get(currentK).get(nextElementNumberToReturn - 1);
    }

    @Override
    public boolean hasElementsForK(int requestedK) {
        System.out.println("has emelents for " + requestedK + !pairsListList.get(requestedK).isEmpty());
        return !pairsListList.get(requestedK).isEmpty();
    }

    @Override
    public void prepareCollection(int requestedK) {
        System.out.println("preparing k: " + requestedK);

        cpQueueToWrite.addAll(pairsListList.get(requestedK));
        flushWritingElements();

        nextElementNumberToReturn = 0;


        requestedKList.set(requestedK + 1);


        while (!requestedListIsReady)
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        requestedListIsReady = false;
    }


    @Override
    public void addNewList(List<AbstractPair> listToAdd) {
        pairsListList.add(listToAdd);

    }

    @Override
    public void clear(int requestedK) {
        pairsListList.get(requestedK).clear();
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


        requestedListIsReady = true;
        return pairsList;
    }

    private List<String> getPairStringList(int requestedK) {
        System.out.println("reading files for k: " + requestedK);
        List<String> fileStringList = new ArrayList<>();
        //read String
        File fileToRead = new File(filePath + "/" + (requestedK - 1) + "/");

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
        System.out.println("succesfully read " + pairStringList.size() + " pair strings for k: " + requestedK);
        return pairStringList;
    }

    @Override
    public void flushWritingElements() {
        flushRequested = true;
        System.out.println("Flushed");
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
