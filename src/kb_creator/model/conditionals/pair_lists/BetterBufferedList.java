package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.cp_buffer.BetterBuffer;

import java.util.List;

public class BetterBufferedList extends AbstractCandidateList {


    public BetterBufferedList(String filePath) {
        System.out.println("created simple buffered list for candidate pairs");
        //candidatePairList = new ArrayList<>();
        cpFileBuffer = new BetterBuffer(filePath);
        Thread cpWriterThread = new Thread(cpFileBuffer);
        cpWriterThread.start();
    }


    public List<AbstractPair> getListForK(int requestedK) {
        return cpFileBuffer.getList(requestedK);
    }

    public List<AbstractPair> readListForK(int requestedK) {
        cpFileBuffer.flush();
        return cpFileBuffer.readList(requestedK);
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