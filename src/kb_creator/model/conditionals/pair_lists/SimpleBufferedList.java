package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.cp_buffer.FasterSimpleBuffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleBufferedList extends AbstractCandidateCollection {
    private int nextElementNumber;
    private int currentK;


    public SimpleBufferedList(String filePath) {
        System.out.println("created simple buffered list for candidate pairs");
        candidatePairList = new ArrayList<>();
        cpFileBuffer = new FasterSimpleBuffer(filePath);
        Thread cpWriterThread = new Thread(cpFileBuffer);
        cpWriterThread.start();
    }




    @Override
    public boolean hasMoreElements() {
        return nextElementNumber + 1 < candidatePairList.get(currentK).size();
    }

    @Override
    public AbstractPair getNextPair() {

        nextElementNumber++;
        return candidatePairList.get(currentK).get(nextElementNumber);
    }

    //todo: has no elements
    @Override
    public boolean hasElementsForK(int requestedK) {
        System.out.println("has elements for " + requestedK + cpFileBuffer.getList(requestedK).isEmpty());
        return cpFileBuffer.getList(requestedK).isEmpty();
    }

    @Override
    public void prepareCollection(int requestedK) {
        nextElementNumber = 0;
        this.currentK = requestedK;
        cpFileBuffer.flushWritingElements();

        cpFileBuffer.prepareCollection(requestedK);

        //todo: this
        //candidatePairList.add(new ArrayList<>());
        //candidatePairList.get(requestedK).addAll(cpFileBuffer.getList(requestedK));

    }

    @Override
    public void addNewList(int k, List<AbstractPair> listToAdd) {

        cpFileBuffer.addCpList(listToAdd);


    }


    @Override
    public void addPair(AbstractPair pairToAdd) {
        cpFileBuffer.addCpToWrite(pairToAdd);
    }


}
