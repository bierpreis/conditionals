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
        System.out.println("new iteration thread finished for k " + k);
    }

}
