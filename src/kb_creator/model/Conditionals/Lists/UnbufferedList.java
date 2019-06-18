package kb_creator.model.Conditionals.Lists;

import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.CpBuffer.CpDummyWriter;

import java.util.ArrayList;
import java.util.List;

public class UnbufferedList extends AbstractCandidateList {

    public UnbufferedList() {
        candidatePairList = new ArrayList<>();
    }

    @Override
    public List<AbstractPair> getListForK(int k) {
        return candidatePairList.get(k);
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
