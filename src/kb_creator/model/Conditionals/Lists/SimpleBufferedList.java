package kb_creator.model.Conditionals.Lists;


import gherkin.lexer.Th;
import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.Writers.CPWriter.CPFileWriter;

import java.util.ArrayList;
import java.util.List;

//todo: this is still the same as unbuffered list. implement some buffering
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
        return candidatePairList.get(requestedK);
    }

    @Override
    public void addNewList(int k, List listToAdd) {
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
