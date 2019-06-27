package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;

import java.util.ArrayList;
import java.util.List;

public class UnbufferedList extends AbstractCandidateCollection {
    private int nextElementNumber;
    private int currentK;
    private List<List<AbstractPair>> candidatePairList;

    public UnbufferedList(String filePath) {
        super(filePath);
        candidatePairList = new ArrayList<>();
    }

    @Override
    public AbstractPair getNextPair() {
        nextElementNumber++;
        if (nextElementNumber < candidatePairList.get(currentK).size())
            return candidatePairList.get(currentK).get(nextElementNumber - 1);
        System.out.println("returned null!");
        return null;
    }

    @Override
    public boolean hasMoreElements() {
        return nextElementNumber + 1 < candidatePairList.get(currentK).size();
    }

    @Override
    public boolean hasElementsForK(int requestedK) {
        return !candidatePairList.get(requestedK).isEmpty();
    }

    @Override
    public void prepareCollection(int k) {
        currentK = k;
        nextElementNumber = 0;
    }

    @Override
    public void addNewList(List<AbstractPair> listToAdd) {
        candidatePairList.add(listToAdd);
        System.out.println("before creating dummy writer");
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
    public int getQueueToWriteSize() {
        return 0;
    }

    @Override
    public void run() {
        //nothing
    }
}
