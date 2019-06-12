package kb_creator.model.Conditionals.Lists;


import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.Writers.CPWriter.CPFileWriter;

import java.util.ArrayList;
import java.util.List;

//todo: this is still the same as unbuffered list. implement some buffering
public class SimpleBufferedList extends AbstractCandidateList {
    private CPFileWriter cpFileWriter;

    //maybe use queue for buffering?

    public SimpleBufferedList(String filePath) {
        candidatePairList = new ArrayList<>();
        cpFileWriter = new CPFileWriter(filePath);
    }

    public List<AbstractPair> getListForK(int requestedK) {
        return candidatePairList.get(requestedK);
    }

    @Override
    public void addNewList(List listToAdd) {
        //todo: not sure what to do here
    }


}
