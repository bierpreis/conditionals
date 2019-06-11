package kb_creator.model.Writers.CPWriter;

import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.Conditionals.Pairs.CandidateNumbersListPair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

//todo: thread for this and combine with bufferedList

public class CPFileWriter extends AbstractCPWriter {

    private Queue<AbstractPair> cpQueueToWrite;

    //todo: rename or delete?
    private Queue<AbstractPair> inconsistentQueue;


    private String folderToSavePath;
    private boolean running;

    public CPFileWriter(String filePath) {
        System.out.println("candidate pairs will be buffered on extra memory");
        if (filePath != null) {
            this.folderToSavePath = filePath + "/tmp/";

            File tmpFile = new File(this.folderToSavePath);
            tmpFile.mkdirs();
        }

        cpQueueToWrite = new ConcurrentLinkedQueue<>();
        inconsistentQueue = new ConcurrentLinkedQueue<>();

    }

    @Override
    public void run() {
        while (running) {
            if (!cpQueueToWrite.isEmpty()) {
                writePair(cpQueueToWrite.poll());
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void addConsistentCp(AbstractPair pairToAdd) {
        cpQueueToWrite.add(pairToAdd);
    }

    public void addInconsistentCp(AbstractPair pairToAdd) {
        inconsistentQueue.add(pairToAdd);
    }

    //todo: write all pairs in 1 file. much more efficient
    private void writePair(AbstractPair candidatePair) {
        File subFolder = new File(folderToSavePath + "/" + candidatePair.getKnowledgeBase().getSize() + "/");
        if (!subFolder.exists())
            subFolder.mkdirs();
        if (folderToSavePath != null)
            try {
                PrintWriter writer = new PrintWriter(subFolder.toString() + "/" + candidatePair.getNumber() + ".txt", "UTF-8");
                writer.print(candidatePair.toFileString());
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

    //todo
    private AbstractPair readNewPairs(int numberOfConditionals) {
        //read String
        File fileToRead = new File(folderToSavePath + "/" + numberOfConditionals + "/");
        System.out.println("files to read: ");

        //todo: candidate pairs should be in order. check this
        AbstractPair candidatePair = new CandidateNumbersListPair("test");
        for (File file : fileToRead.listFiles()) {
            if (!file.isDirectory()) {
                System.out.println(file.getName());

            }


        }
        return new CandidateNumbersListPair("test");

    }

    public int getInconsistentCounter() {
        return inconsistentQueue.size();
    }

    public int getQueueToWriteSize() {
        return cpQueueToWrite.size();
    }


    public Queue<AbstractPair> getInconsistentQueue() {
        return inconsistentQueue;
    }

    public void stop() {
        running = false;
    }
}
