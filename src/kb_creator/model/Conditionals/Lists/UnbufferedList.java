package kb_creator.model.Conditionals.Lists;

import kb_creator.model.Conditionals.Pairs.CandidateNumbersListPair;

import java.util.ArrayList;
import java.util.List;

public class UnbufferedList extends AbstractCandidateList {
    List<List<CandidateNumbersListPair>> cpList;

    public UnbufferedList() {
        cpList = new ArrayList<>();
    }

    @Override
    public List<List<CandidateNumbersListPair>> getList() {
        return cpList;
    }
}
