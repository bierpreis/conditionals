package kb_creator.model.buffer.ram;

import kb_creator.model.logic.KnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.CompressedPair;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class NewIterationThread implements Runnable {
    protected BlockingQueue<AbstractPair> inputQueue;
    protected List<List<AbstractPair>> candidatePairList;
    protected volatile boolean running = true;
    protected int k;

    private BlockingQueue<KnowledgeBase> consistentQueue;

    public NewIterationThread(BlockingQueue<AbstractPair> inputQueue, BlockingQueue<KnowledgeBase> consistentQueue, List<List<AbstractPair>> candidatePairList, int k) {
        this.inputQueue = inputQueue;
        this.consistentQueue = consistentQueue;
        this.candidatePairList = candidatePairList;
        this.k = k;
    }

    @Override
    public void run() {

        while (running) {
            AbstractPair pairToAdd;
            try {
                pairToAdd = inputQueue.take();
            } catch (InterruptedException e) {
                running = false;
                break; //this is added new
            }
            try {
                consistentQueue.put(pairToAdd.getKnowledgeBase());
            } catch (InterruptedException e) {
                running = false;
                break; //this is added new
            }
            candidatePairList.get(k).add(new CompressedPair(pairToAdd));
        }
    }
}
