package kb_creator.model.buffer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.CompressedArrayPair;
import kb_creator.model.propositional_logic.NewConditional;

import java.util.ArrayList;
import java.util.List;

public class DummyPairBuffer extends AbstractPairBuffer {
    private volatile int nextElementNumber;
    private volatile List<List<AbstractPair>> candidatePairList;
    private volatile int k;

    public DummyPairBuffer(String filePath) {
        super(filePath);
        candidatePairList = new ArrayList<>();
    }

    @Override
    public AbstractPair getNextPair(int k) {

        synchronized (this) {
            nextElementNumber++;
            return candidatePairList.get(k - 1).get(nextElementNumber - 1);
        }

    }


    @Override
    public boolean hasMoreElementsForK(int k) {
        synchronized (this) {
            return (nextElementNumber) < (candidatePairList.get(k - 1).size());
        }
    }

    @Override
    public boolean hasElementsForNextK(int k) {
        return !candidatePairList.get(k - 1).isEmpty();
    }

    @Override
    public void prepareIteration(int k) {
        System.out.println("preparing iteration: " + k);
        nextElementNumber = 0;
        this.k = k;
    }

    @Override
    protected void clear(int requestedK) {
        System.out.println("clearing list for k " + requestedK);
        //don't clear list(-1) it wont work
        if (requestedK > 0)
            candidatePairList.get(requestedK).clear();
    }

    @Override
    public void finishIteration(int requestedK) {
        System.out.println("finishing iteration: " + requestedK);
        lastIterationPairAmount = candidatePairList.get(requestedK).size();


        clear(requestedK - 1);
    }

    @Override
    public void addNewList(List<AbstractPair> listToAdd) {
        candidatePairList.add(listToAdd);
    }

    @Override
    public void addPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidatesToAdd) {
        candidatePairList.get(k).add(new CompressedArrayPair(knowledgeBase, candidatesToAdd));
    }

    @Override
    public void addPair(AbstractPair pair) {
        synchronized (this) {
            candidatePairList.get(k).add(pair);
        }
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


    @Override
    public boolean checkIfShouldWrite() {
        //intentionally nothing
        return false;
    }

    @Override
    public boolean checkIfShouldRead() {
        //intentionally nothing
        return false;
    }

    @Override
    public void notifyBuffer() {
        //intentionally nothing
    }

}
