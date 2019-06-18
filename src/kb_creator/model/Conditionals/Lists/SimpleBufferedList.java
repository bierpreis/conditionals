package kb_creator.model.Conditionals.Lists;

import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.Writers.CPWriter.CpFileBuffer;

import java.util.List;

public class SimpleBufferedList extends AbstractCandidateList {
    private CpFileBuffer cpFileBuffer;


    public SimpleBufferedList(String filePath) {
        System.out.println("created simple buffered list for candidate pairs");
        //candidatePairList = new ArrayList<>();
        cpFileBuffer = new CpFileBuffer(filePath);
        Thread cpWriterThread = new Thread(cpFileBuffer);
        cpWriterThread.start();
    }


    public List<AbstractPair> getListForK(int requestedK) {
        System.out.println("asked for return list for k: " + requestedK);
        return cpFileBuffer.getList(requestedK);
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
