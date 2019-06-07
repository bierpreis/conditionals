package kb_creator.model.Conditionals.Lists;

import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.Conditionals.Pairs.CandidateNumbersListPair;

import java.util.ArrayList;
import java.util.List;

public class UnbufferedList extends AbstractCandidateList {
    List<List<AbstractPair>> cpList;

    public UnbufferedList() {
        cpList = new ArrayList<>();
    }

    @Override
    public List<List<AbstractPair>> getList() {
        return cpList;
    }
}
