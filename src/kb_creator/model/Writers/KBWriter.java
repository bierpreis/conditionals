package kb_creator.model.Writers;

import kb_creator.model.Conditionals.KnowledgeBase;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

//todo: there should be 1 writer for all amounts
public class KBWriter implements Runnable {
    private Queue<KnowledgeBase> consistentQueue;
    private Queue<KnowledgeBase> inconsistentQueue;

    public void run() {

        while (true) {
            //todo: this sucks
            if (inconsistentQueue != null) {
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

    private String consistentKbFolder;
    private String inconsistentKbFolder;

    public KBWriter(String filePathToSave, int kbAmount) {
        System.out.println("kbwriter: " + kbAmount);
        //if filepath is null nothing should be saved so nothing should happen here
        if (filePathToSave != null) {
            filePathToSave = filePathToSave + "/" + kbAmount;


            consistentKbFolder = filePathToSave + "/Consistent/";
            inconsistentKbFolder = filePathToSave + "/Inconsistent/";

            //create folders

            File consistentFolder = new File(consistentKbFolder);
            consistentFolder.mkdirs();

            File inconsistentFolder = new File(inconsistentKbFolder);
            inconsistentFolder.mkdirs();

            consistentQueue = new ConcurrentLinkedQueue();
            inconsistentQueue = new ConcurrentLinkedQueue();

        }
    }

    public void addConsistentKB(KnowledgeBase kbToAdd) {
        //System.out.println("addinc inconsistent");
        //todo: this sucks
        if (consistentQueue != null)
            consistentQueue.add(kbToAdd);
    }

    public void addInconsistentKB(KnowledgeBase kbToAdd) {
        //System.out.println("adding inConsistent");
        if (inconsistentQueue != null)
            inconsistentQueue.add(kbToAdd);
    }

    private void writeConsistentKBToFile(KnowledgeBase knowledgeBase) {
        System.out.println("writing consistent..." + knowledgeBase.toString());
        if (consistentKbFolder != null)
            try {


                PrintWriter writer = new PrintWriter(consistentKbFolder + knowledgeBase.getKbNumber() + ".txt", "UTF-8");
                writer.print(knowledgeBase.toFileString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void writeInconsistentKBToFile(KnowledgeBase knowledgeBase) {
        System.out.println("writing inconsistent");
        if (inconsistentKbFolder != null)
            try {
                PrintWriter writer = new PrintWriter(inconsistentKbFolder + "/" + knowledgeBase.getKbNumber() + ".txt", "UTF-8");
                writer.print(knowledgeBase.toFileString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


}

