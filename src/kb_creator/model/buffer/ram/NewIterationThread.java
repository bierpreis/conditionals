package kb_creator.model.buffer.ram;

import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.CompressedPair;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class NewIterationThread implements Runnable {
    protected BlockingQueue<AbstractPair> inputQueue;
    protected List<List<AbstractPair>> candidatePairList;
    protected volatile boolean running = true;
    protected int k;

    public NewIterationThread(BlockingQueue<AbstractPair> inputQueue, List<List<AbstractPair>> candidatePairList, int k) {
        this.inputQueue = inputQueue;
        this.candidatePairList = candidatePairList;
        this.k = k;
    }

    @Override
    public void run() {
        System.out.println("new iteration thread started for k " + k);
        while (running) {
            try {
                candidatePairList.get(k).add(new CompressedPair(inputQueue.take()));
            } catch (InterruptedException e) {
                running = false;
            }
        }
        System.out.println("new iteration thread finished for k " + k);
    }
}
