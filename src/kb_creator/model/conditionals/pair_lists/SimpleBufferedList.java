package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.cp_buffer.FastCpFileBuffer;

import java.util.Collection;
import java.util.List;

public class SimpleBufferedList extends AbstractCandidateCollection {


    public SimpleBufferedList(String filePath) {
        System.out.println("created simple buffered list for candidate pairs");
        //candidatePairList = new ArrayList<>();
        cpFileBuffer = new FastCpFileBuffer(filePath);
        Thread cpWriterThread = new Thread(cpFileBuffer);
        cpWriterThread.start();
    }

    @Override
    public Collection<AbstractPair> getListForK(int requestedK) {
        return cpFileBuffer.getList(requestedK);
    }

    @Override
    public Collection<AbstractPair> readListForK(int requestedK){
        cpFileBuffer.flush();
        return cpFileBuffer.readPairs(requestedK);
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
