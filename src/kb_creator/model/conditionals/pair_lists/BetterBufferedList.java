package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.cp_buffer.QueueBuffer;

import java.util.Collection;
import java.util.List;

public class BetterBufferedList extends AbstractCandidateCollection {


    public BetterBufferedList(String filePath) {
        System.out.println("created simple buffered list for candidate pairs");
        //candidatePairList = new ArrayList<>();
        cpFileBuffer = new QueueBuffer(filePath);
        Thread cpWriterThread = new Thread(cpFileBuffer);
        cpWriterThread.start();
    }


    public Collection<AbstractPair> getListForK(int requestedK) {
        return cpFileBuffer.getList(requestedK);
    }


    //todo
    @Override
    public boolean hasMoreElements() {
        return false;
    }

    //todo
    @Override
    public AbstractPair getNextElement() {
        return null;
    }

    //todo
    @Override
    public boolean hasElementsForK(int requestedK) {
        return false;
    }

    //todo
    @Override
    public void prepareCollection(int requestedK) {

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
