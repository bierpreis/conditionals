package kb_creator.model.Writers;

import kb_creator.model.Conditionals.KnowledgeBase;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KBWriter implements Runnable {
    private Queue<KnowledgeBase> consistentQueue;
    private Queue<KnowledgeBase> inconsistentQueue;
    private String rootFilePath;

    public void run() {

        while (true) {
            //rootfilepath is null when saving is not requested. maybe improve by one abstract writer and one like this and one fake
            if (rootFilePath != null) {
                if (!consistentQueue.isEmpty())
                    writeConsistentKBToFile(consistentQueue.poll());
                if (!inconsistentQueue.isEmpty())
                    writeInconsistentKBToFile(inconsistentQueue.poll());
                if (consistentQueue.isEmpty() && inconsistentQueue.isEmpty()) try {
                    System.out.println("sleep inc: " + inconsistentQueue.size() + " cons: " + consistentQueue.size());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }


    public KBWriter(String filePathToSave) {

        //if filepath is null nothing should be saved so nothing should happen here
        if (filePathToSave != null) {
            rootFilePath = filePathToSave;
/*            consistentKbFolder = filePathToSave + "/Consistent/";
            inconsistentKbFolder = filePathToSave + "/Inconsistent/";

            //create folders

            File consistentFolder = new File(consistentKbFolder);
            consistentFolder.mkdirs();

            File inconsistentFolder = new File(inconsistentKbFolder);
            inconsistentFolder.mkdirs();*/

            consistentQueue = new ConcurrentLinkedQueue();
            inconsistentQueue = new ConcurrentLinkedQueue();

        }
    }

    public void addConsistentKB(KnowledgeBase kbToAdd) {
        if (consistentQueue != null)
            consistentQueue.add(kbToAdd);
    }

    public void addInconsistentKB(KnowledgeBase kbToAdd) {
        //System.out.println("adding inConsistent");
        if (inconsistentQueue != null)
            inconsistentQueue.add(kbToAdd);
    }

    private void writeConsistentKBToFile(KnowledgeBase knowledgeBase) {

        if (rootFilePath != null) {
            String filePath = rootFilePath + knowledgeBase.getSize() + "/" + "consistent/";
            try {
                File consistentFolder = new File(filePath);
                consistentFolder.mkdirs();

                PrintWriter writer = new PrintWriter(filePath + knowledgeBase.getKbNumber() + ".txt", "UTF-8");
                writer.print(knowledgeBase.toFileString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeInconsistentKBToFile(KnowledgeBase knowledgeBase) {
        if (rootFilePath != null) {
            String filePath = rootFilePath + knowledgeBase.getSize() + "/" + "inconsistent/";

            File inconsistentFolder = new File(filePath);
            inconsistentFolder.mkdirs();

            try {

                PrintWriter writer = new PrintWriter(filePath + knowledgeBase.getKbNumber() + ".txt", "UTF-8");
                writer.print(knowledgeBase.toFileString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

