package kb_creator.model.Conditionals.Lists;


import kb_creator.model.Conditionals.Pairs.AbstractPair;

import java.util.ArrayList;
import java.util.List;

//todo: this is still the same as unbuffered list. implement some buffering
public class BufferedList extends AbstractCandidateList {
    private String filePath;

    //maybe use queue for buffering?

    public BufferedList(String filePath) {
        this.filePath = filePath;
        candidatePairList = new ArrayList<>();
    }

    public List<List<AbstractPair>> getList() {
        return candidatePairList;
    }


}
