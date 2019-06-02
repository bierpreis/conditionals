package kb_creator.model.Conditionals.Lists;

import kb_creator.model.Conditionals.CandidatePair;

import java.util.ArrayList;
import java.util.List;

public class UnbufferedList extends AbstractCandidateList {
    List<List<CandidatePair>> cpList;

    public UnbufferedList() {
        cpList = new ArrayList<>();
    }

    @Override
    public List<List<CandidatePair>> getList() {
        return cpList;
    }
}
