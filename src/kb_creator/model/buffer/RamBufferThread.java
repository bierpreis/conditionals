package kb_creator.model.buffer;

import kb_creator.model.pairs.CompressedPair;
import kb_creator.model.pairs.RealPair;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class RamBufferThread implements Runnable{
    private BlockingQueue<RealPair> inputQueue;
    private List<List<CompressedPair>> candidatePairList;
    private volatile boolean running = true;
    private int k;

    public RamBufferThread(BlockingQueue<RealPair> inputQueue, List<List<CompressedPair>> candidatePairList, int k){
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
