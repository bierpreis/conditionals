package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.cp_buffer.SimpleBuffer;

import java.util.List;

public class SimpleBufferedList extends AbstractCandidateCollection {
    private int currentK;


    public SimpleBufferedList(String filePath) {
        System.out.println("created simple buffered list for candidate pairs");
        cpFileBuffer = new SimpleBuffer(filePath);
        Thread cpWriterThread = new Thread(cpFileBuffer);
        cpWriterThread.start();
    }


    //todo: dont use cp list but cp buffer?! build sth in cp buffer that it knows when list is empty
    @Override
    public boolean hasMoreElements() {
        return cpFileBuffer.hasMoreElements(currentK);
    }

    @Override
    public AbstractPair getNextPair() {
        return cpFileBuffer.getNextPair(currentK);
    }


    @Override
    public boolean hasElementsForK(int requestedK) {
        return !cpFileBuffer.hasElementsForK(requestedK);
    }

    @Override
    public void prepareCollection(int requestedK) {
        this.currentK = requestedK;
        cpFileBuffer.flushWritingElements();

        cpFileBuffer.prepareCollection(requestedK);


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
