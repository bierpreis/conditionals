package kb_creator.model.buffer.simple_ram;

import kb_creator.model.buffer.ram.CompressedRamBuffer;

import java.util.ArrayList;
import java.util.Collections;

public class SimpleRamBuffer extends CompressedRamBuffer {

    //todo: consistent counter doesnt work in simple buffer
    @Override
    public void prepareIteration(int k) {
        candidatePairList.add(Collections.synchronizedList(new ArrayList<>()));

        nextIterationThread = new Thread(new SimpleNewIterationThread(nextIterationQueue, consistentQueue, candidatePairList, k));
        nextIterationThread.setName("new iteration thread for k " + k);
        nextIterationThread.start();

        //don't start this for k = 0
        //there is no last iteration for 0
        if (k != 0) {

            lastIterationThread = new Thread(lastIterationThreadObject = new SimpleLastIterationThread(lastIterationQueue, candidatePairList, k));
            lastIterationThread.setName("last iteration thread for k " + k);
            lastIterationThread.start();
        }

    }
}
