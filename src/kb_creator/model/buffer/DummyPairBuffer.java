package kb_creator.model.buffer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.CompressedArrayPair;
import kb_creator.model.propositional_logic.NewConditional;

import java.util.ArrayList;
import java.util.List;

public class DummyPairBuffer extends AbstractPairBuffer {
    private int nextElementNumber;
    private List<List<AbstractPair>> candidatePairList;

    //todo: test finish and prepare iterations
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

    //todo
    @Override
    public void prepareIteration(int k) {
        nextElementNumber = 0;
    }

    @Override
    protected void clear(int requestedK) {
        //dont clear list(-1) it wont work
        if (requestedK > 0)
            candidatePairList.get(requestedK).clear();
    }

    @Override
    public void finishIteration(int requestedK) {
        lastIterationPairAmount = candidatePairList.get(requestedK + 1).size();


        clear(requestedK - 1);
    }

    @Override
    public void addNewList(List<AbstractPair> listToAdd) {
        candidatePairList.add(listToAdd);
    }

    @Override
    public void addPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidatesToAdd) {
        candidatePairList.get(candidatePairList.size() - 1).add(new CompressedArrayPair(knowledgeBase, candidatesToAdd));
    }


    @Override
    public int getQueueToWriteSize() {
        return 0;
    }

    @Override
    public void run() {
        //nothing
    }

    @Override
    public int getReaderBufferSize() {
        return 0;
    }
}
