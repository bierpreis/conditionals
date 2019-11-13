package kb_creator.model.buffer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.CompressedArrayPair;
import kb_creator.model.propositional_logic.PConditional;

import java.util.ArrayList;
import java.util.List;

public class DummyPairBuffer extends AbstractPairBuffer {
    private int nextElementNumber;
    private List<List<AbstractPair>> candidatePairList;
    private int k;

    public DummyPairBuffer() {
        super(null);
        candidatePairList = new ArrayList<>();
    }


    //iteration change methods

    @Override
    public boolean hasMoreElementsForK(int k) {
        return (nextElementNumber) < (candidatePairList.get(k - 1).size());
    }

    @Override
    public boolean hasElementsForIteration(int k) {
        return !candidatePairList.get(k - 1).isEmpty();
    }

    @Override
    public void prepareIteration(int k) {
        System.out.println("preparing iteration: " + k);
        nextElementNumber = 0;
        this.k = k;
    }

    @Override
    protected void deleteOldData(int requestedK) {
        //System.out.println("clearing list for k " + requestedK);

        //don't clear list(-1) it wont work :D
        if (requestedK > 0)
            candidatePairList.get(requestedK).clear();
    }

    @Override
    public void finishIteration(int requestedK) {
        System.out.println("finishing iteration: " + requestedK);
        lastIterationPairAmount = candidatePairList.get(requestedK).size();
        deleteOldData(requestedK - 1);
    }

    @Override
    public void stopLoop() {
        //nothing
    }


    // add pair methods

    @Override
    public void addNewList(List<AbstractPair> listToAdd) {
        candidatePairList.add(listToAdd);
    }

    @Override
    public void addPair(AbstractKnowledgeBase knowledgeBase, List<PConditional> candidatesToAdd) {
        candidatePairList.get(k).add(new CompressedArrayPair(knowledgeBase, candidatesToAdd));
    }

    @Override
    public void addPair(AbstractPair pair) {
        candidatePairList.get(k).add(pair);
    }


    //getters

    @Override
    public AbstractPair getNextPair(int k) {
        nextElementNumber++;
        return candidatePairList.get(k - 1).get(nextElementNumber - 1);
    }

    @Override
    public int getQueueToWriteSize() {
        return 0;
    }


    @Override
    public int getReaderBufferSize() {
        return 0;
    }

}
