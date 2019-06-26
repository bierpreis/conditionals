package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.cp_buffer.CpDummyWriter;

import java.util.ArrayList;
import java.util.List;

public class UnbufferedList extends AbstractCandidateCollection {
    private int nextElementNumber;
    private int currentK;

    public UnbufferedList() {
        candidatePairList = new ArrayList<>();
    }

    @Override
    public AbstractPair getNextElement() {
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
    public void addNewList(int k, List<AbstractPair> listToAdd) {
        System.out.println("added new list for k: " + k);
        candidatePairList.add(listToAdd);
        System.out.println("before creating dummy writer");
        cpFileBuffer = new CpDummyWriter(null);
    }

    @Override
    public void addPair(AbstractPair pairToAdd) {
        candidatePairList.get(candidatePairList.size() - 1).add(pairToAdd);
    }

}
