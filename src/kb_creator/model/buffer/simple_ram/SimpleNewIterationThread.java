package kb_creator.model.buffer.simple_ram;

import kb_creator.model.buffer.ram.NewIterationThread;
import kb_creator.model.logic.KnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.CompressedPair;
import kb_creator.model.pairs.RealPair;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class SimpleNewIterationThread extends NewIterationThread {

    public SimpleNewIterationThread(BlockingQueue<AbstractPair> inputQueue, BlockingQueue<KnowledgeBase> consistentQueue, List<List<AbstractPair>> candidatePairList, int k) {
        super(inputQueue, consistentQueue, candidatePairList, k);
    }

    @Override
    public void run() {
        System.out.println("new iteration thread started for k " + k);
        while (running) {
            try {
                candidatePairList.get(k).add(new RealPair(inputQueue.take()));
            } catch (InterruptedException e) {
                running = false;
            }
        }
        System.out.println("new iteration thread finished for k " + k);
    }

}
