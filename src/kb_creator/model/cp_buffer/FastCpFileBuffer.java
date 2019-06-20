package kb_creator.model.cp_buffer;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.conditionals.pairs.CandidateNumbersArrayPair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class FastCpFileBuffer extends AbstractCPWriter {
    private final int numberOfPairsInFile = 100;


    private AtomicInteger requestedKList;
    private List<AbstractPair> requestedList;
    private volatile boolean requestedListIsReady;

    private String folderToSavePath;
    private boolean running;

    public FastCpFileBuffer(String filePath) {
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
        status = CandidateStatus.NOT_STARTED;

    }

    @Override
    public void run() {
        while (running) {
            if (cpQueueToWrite.size() > 10) {
                writeAllPairs(cpQueueToWrite);
                status = CandidateStatus.WRITING;
            } else if (requestedKList.get() != 0) {
                requestedList = readAllPairs(requestedKList.get());
                status = CandidateStatus.READING;
            } else
                try {
                    status = CandidateStatus.SLEEPING;
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
        System.out.println("write all pairs");
        int alreadyWrittenNumberOfFiles = 0;
        File subFolder = new File(folderToSavePath + "/" + ((AbstractPair) queueToWrite.peek()).getKnowledgeBase().getSize() + "/");
        if (!subFolder.exists())
            subFolder.mkdirs();
        if (folderToSavePath != null)
            try {

                while (!queueToWrite.isEmpty()) {
                    //add leading zeros so the files will be soreted in correct order in their folder
                    String fileName = String.format("%05d", alreadyWrittenNumberOfFiles);

                    PrintWriter writer = new PrintWriter(subFolder.toString() + "/" + fileName + ".txt", "UTF-8");

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < numberOfPairsInFile && !queueToWrite.isEmpty(); i++) {


                        AbstractPair pairToWrite = (AbstractPair) queueToWrite.poll();
                        sb.append(pairToWrite.toFileString());
                        sb.append("END_PAIR");
                        pairToWrite.deleteCandidates();
                        pairToWrite.deleteKB();

                    }
                    writer.print(sb.toString());
                    writer.close();
                }

                //delete data which is not needed anymore to free space


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

    //todo implement for long files
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

        for (String stringFromFile : stringList) {
            pairsList.add(new CandidateNumbersArrayPair(stringFromFile));
        }

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
