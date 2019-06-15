package kb_creator.model.Writers.CPWriter;

import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.Conditionals.Pairs.CandidateNumbersArrayPair;
import kb_creator.model.Conditionals.Pairs.CandidateNumbersListPair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class CpFileBuffer extends AbstractCPWriter {

    private Queue<AbstractPair> cpQueueToWrite;
    private AtomicInteger requestedKList;
    private List<AbstractPair> requestedList;
    private volatile boolean requestedListIsReady;

    private String folderToSavePath;
    private boolean running;

    public CpFileBuffer(String filePath) {
        running = true;
        System.out.println("candidate pairs will be buffered on extra memory");
        if (filePath != null) {
            this.folderToSavePath = filePath + "/tmp/";

            File tmpFile = new File(this.folderToSavePath);
            tmpFile.mkdirs();
        }

        cpQueueToWrite = new ConcurrentLinkedQueue<>();
        requestedKList = new AtomicInteger(0);
        requestedListIsReady = false;
    }

    @Override
    public void run() {
        while (running) {
            if (cpQueueToWrite.peek() != null) {
                writePair(cpQueueToWrite.poll());
            } else if (requestedKList.get() != 0) {
                requestedList = readAllPairs(requestedKList.get());
            } else
                try {
                    System.out.println("cp writer is sleeping");
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

    //todo: how to put k in there? maybe get correkt k by asking pair.getKb.getSize?
    public void addCpList(int k, List<AbstractPair> listToAdd) {
        cpQueueToWrite.addAll(listToAdd);
    }


    //todo: write all pairs in 1 file. much more efficient

    private void writePair(AbstractPair candidatePair) {
        File subFolder = new File(folderToSavePath + "/" + candidatePair.getKnowledgeBase().getSize() + "/");
        if (!subFolder.exists())
            subFolder.mkdirs();
        if (folderToSavePath != null)
            try {
                //add leading zeros so the files will be soreted in correct order in their folder
                String fileName = String.format("%08d", candidatePair.getNumber());

                PrintWriter writer = new PrintWriter(subFolder.toString() + "/" + fileName + ".txt", "UTF-8");


                writer.print(candidatePair.toFileString());


                //todo: maybe the whole cp can be deleted somewhere? would be better
                candidatePair.deleteCandidates();
                candidatePair.deleteKB();
                writer.close();
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
        List<String> stringList = getPairStringList(requestedK);
        List<AbstractPair> pairsList = new ArrayList<>(stringList.size());

        for (String stringFromFile : stringList)
            pairsList.add(new CandidateNumbersArrayPair(stringFromFile));

        requestedListIsReady = true;
        return pairsList;
    }

    private List<String> getPairStringList(int requestedK) {

        //read String
        File fileToRead = new File(folderToSavePath + "/" + requestedK + "/");

        File[] filesArray = fileToRead.listFiles();

        Arrays.sort(filesArray);

        List<String> stringList = new ArrayList<>();

        try {
            for (File file : filesArray) {
                Scanner fileScanner = new Scanner(file);

                StringBuilder sb = new StringBuilder();

                while (fileScanner.hasNextLine()) {
                    sb.append(fileScanner.nextLine());
                    sb.append("\n");
                }

                stringList.add(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }
}
