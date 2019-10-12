package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class KBWriterThread implements Runnable {
    private String folderName;
    private String rootFilePath;
    private BlockingQueue<AbstractKnowledgeBase> queue;
    private boolean running = true;

    private int iterationCounter = 0;
    private int totalCounter = 0;

    public KBWriterThread(String rootFilePath, String folderName, BlockingQueue<AbstractKnowledgeBase> queue) {
        this.folderName = folderName;
        this.queue = queue;
        this.rootFilePath = rootFilePath;

        Thread.currentThread().setName("KBWriterThread");
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
    }

    public void newIteration() {
        iterationCounter = 0;
    }

    private void writeConsistentKbToFile(AbstractKnowledgeBase knowledgeBase) {


        String filePath = rootFilePath + knowledgeBase.getSize() + "/" + folderName + "/";
        try {
            iterationCounter++;
            totalCounter++;
            //todo: where is kb 31 with sig ab??? must be lost somewere!
            //todo: why does iteration 2 cconsistent numbers ontinue counting from iteration 1?
            knowledgeBase.setKbNumber(iterationCounter);
            File consistentFolder = new File(filePath);
            consistentFolder.mkdirs();

            PrintWriter writer = new PrintWriter(filePath + knowledgeBase.getKbNumber() + ".txt", "UTF-8");

            writer.print(knowledgeBase.toFileString());

            writer.close();


        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public int getIterationCounter() {
        return iterationCounter;
    }

    public int getTotalCounter() {
        return totalCounter;
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
