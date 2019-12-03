package kb_creator.model.buffer;

import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.CompressedPair;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class NewIterationThread implements Runnable{
    private BlockingQueue<AbstractPair> inputQueue;
    private List<List<AbstractPair>> candidatePairList;
    private volatile boolean running = true;
    private int k;

    public NewIterationThread(BlockingQueue<AbstractPair> inputQueue, List<List<AbstractPair>> candidatePairList, int k){
        this.inputQueue = inputQueue;
        this.candidatePairList = candidatePairList;
        this.k = k;
    }

    @Override
    public void run() {
        while (running) {
            try {
                candidatePairList.get(k).add(new CompressedPair(inputQueue.take()));
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }
}
