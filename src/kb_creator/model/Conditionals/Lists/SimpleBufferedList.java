package kb_creator.model.Conditionals.Lists;

import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.Writers.CPWriter.CPFileWriter;
import kb_creator.model.Writers.CPWriter.CpFileReader;

import java.util.ArrayList;
import java.util.List;

public class SimpleBufferedList extends AbstractCandidateList {
    private CPFileWriter cpFileWriter;
    private String filePath;

    //maybe use queue for buffering?

    public SimpleBufferedList(String filePath) {
        System.out.println("created simple buffered list for candidate pairs");
        candidatePairList = new ArrayList<>();
        this.filePath = filePath;
    }


    public List<AbstractPair> getListForK(int requestedK) {
        System.out.println("asked for return list for k: " + requestedK);
        CpFileReader reader = new CpFileReader(requestedK, filePath);
        reader.readAllPairs();
        return candidatePairList.get(requestedK);
    }

    @Override
    public void addNewList(int k, List listToAdd) {
        System.out.println("added new list for k: " + k);
        candidatePairList.add(listToAdd);
        cpFileWriter = new CPFileWriter(k, filePath);
        cpFileWriter.addCpList(listToAdd);
        Thread cpWriterThread = new Thread(cpFileWriter);
        cpWriterThread.start();

    }

    @Override
    public void addPair(AbstractPair pairToAdd) {
        cpFileWriter.addCpToWrite(pairToAdd);
    }


}
