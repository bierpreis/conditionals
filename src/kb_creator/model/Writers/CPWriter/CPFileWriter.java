package kb_creator.model.Writers.CPWriter;

import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.Conditionals.Pairs.CandidateNumbersListPair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CPFileWriter extends AbstractCPWriter {

    private Queue<AbstractPair> cpQueueToWrite;


    private String folderToSavePath;
    private boolean running;

    public CPFileWriter(int k, String filePath) {
        running = true;
        System.out.println("candidate pairs will be buffered on extra memory");
        if (filePath != null) {
            this.folderToSavePath = filePath + "/tmp/";

            File tmpFile = new File(this.folderToSavePath);
            tmpFile.mkdirs();
        }

        cpQueueToWrite = new ConcurrentLinkedQueue<>();

    }

    @Override
    public void run() {
        while (running) {
            if (cpQueueToWrite.peek() != null) {
                writePair(cpQueueToWrite.poll());
            } else
                try {
                    //System.out.println("cp writer is sleeping");
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

    public void addCpList(List<AbstractPair> listToAdd) {
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
}
