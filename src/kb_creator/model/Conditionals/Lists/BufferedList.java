package kb_creator.model.Conditionals.Lists;


import kb_creator.model.Conditionals.CandidatePair;

import java.util.ArrayList;
import java.util.List;

//todo: this is still the same as unbuffered list. implement some buffering
public class BufferedList extends AbstractCandidateList {
    private String filePath;
    private List<List<CandidatePair>> cpList;

    //maybe use queue for buffering?

    public BufferedList(String filePath) {
        this.filePath = filePath;
        cpList = new ArrayList<>();
    }

    public List<List<CandidatePair>> getList() {
        return cpList;
    }


}
