package kb_creator.model.conditionals.buffer;

import kb_creator.model.conditionals.pairs.AbstractPair;

import java.util.ArrayList;
import java.util.List;

public class DummyPairBuffer extends AbstractPairBuffer {
    private int nextElementNumber;
    private List<List<AbstractPair>> candidatePairList;

    public DummyPairBuffer(String filePath) {
        super(filePath);
        candidatePairList = new ArrayList<>();
    }

    @Override
    public AbstractPair getNextPair(int currentK) {
        nextElementNumber++;
        if (nextElementNumber < candidatePairList.get(currentK).size())
            return candidatePairList.get(currentK).get(nextElementNumber - 1);

        throw new RuntimeException("Dummy buffer failed!");
    }

    @Override
    public boolean hasMoreElements(int currentK) {
        return nextElementNumber + 1 < candidatePairList.get(currentK).size();
    }

    @Override
    public boolean hasElementsForK(int requestedK) {
        return !candidatePairList.get(requestedK).isEmpty();
    }

    @Override
    public void prepareIteration(int k) {
        nextElementNumber = 0;
    }

    @Override
    public void clear(int requestedK) {
        candidatePairList.get(requestedK).clear();
    }

    @Override
    public void addNewList(List<AbstractPair> listToAdd) {
        candidatePairList.add(listToAdd);
    }

    @Override
    public void addPair(AbstractPair pairToAdd) {
        candidatePairList.get(candidatePairList.size() - 1).add(pairToAdd);
    }

    @Override
    public void flushWritingElements() {
        //nothing
    }

    @Override
    public void finishIteration(int requestedK) {
        if (requestedK != 1)
            lastIterationPairAmount = candidatePairList.get(requestedK + 1).size();
    }

    @Override
    public int getQueueToWriteSize() {
        return 0;
    }

    @Override
    public void run() {
        //nothing
    }
}
