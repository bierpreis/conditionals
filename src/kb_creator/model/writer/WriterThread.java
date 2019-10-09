package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class WriterThread implements Runnable {
    private String folderName;
    private String rootFilePath;
    private BlockingQueue<AbstractKnowledgeBase> queue;
    private boolean running = true;

    private int counter = 0;

    public WriterThread(String rootFilePath, String folderName, BlockingQueue<AbstractKnowledgeBase> queue) {
        this.folderName = folderName;
        this.queue = queue;
        this.rootFilePath = rootFilePath;
    }

    @Override
    public void run() {
        System.out.println("New Writer Thread started for " + folderName + " kbs");
        while (running) {
            try {
                writeConsistentKbToFile(queue.take());
            } catch (InterruptedException e) {
                //intentionally nothing
            }
        }
        System.out.println("writer stopped: " + folderName);
    }

    private void writeConsistentKbToFile(AbstractKnowledgeBase knowledgeBase) {


        String filePath = rootFilePath + knowledgeBase.getSize() + "/" + folderName + "/";
        try {

            File consistentFolder = new File(filePath);
            consistentFolder.mkdirs();

            PrintWriter writer = new PrintWriter(filePath + knowledgeBase.getKbNumber() + ".txt", "UTF-8");

            writer.print(knowledgeBase.toFileString());

            writer.close();
            counter++;

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public int getCounter() {
        return counter;
    }

    public int getSize() {
        return queue.size();
    }

    public void stop() {

        //this makes sure queue is empty before it stops
        while (!queue.isEmpty())
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        running = false;
    }
}
