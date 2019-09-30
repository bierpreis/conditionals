package kb_creator.model.creator;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;

import java.util.concurrent.BlockingQueue;

public class CandidateThread implements Runnable {

    private int threadNumber;
    private BlockingQueue<AbstractKnowledgeBase> consistentQueue;
    private BlockingQueue<AbstractKnowledgeBase> inconsistentQueue;
    private BlockingQueue<AbstractPair> pairsQueue;

    //todo: add queues
    public CandidateThread(int threadNumber, BlockingQueue<AbstractKnowledgeBase> consistentQueue, BlockingQueue<AbstractKnowledgeBase> inconsistentQueue, BlockingQueue<AbstractPair> candidatesQueue) {
        this.threadNumber = threadNumber;

        this.consistentQueue = consistentQueue;
        this.inconsistentQueue = inconsistentQueue;
        this.pairsQueue = candidatesQueue;

    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            //todo: sth useful
            System.out.println("thread " + threadNumber + " running...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("interrupted exception! (" + threadNumber + ")");
            }
        }
    }
}
