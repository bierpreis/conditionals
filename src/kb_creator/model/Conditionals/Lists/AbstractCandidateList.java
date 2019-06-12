package kb_creator.model.Conditionals.Lists;

import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.Conditionals.Pairs.CandidateNumbersListPair;

import java.util.List;

public abstract class AbstractCandidateList {
    protected  List<List<AbstractPair>> candidatePairList;

    abstract public List<List<AbstractPair>> getList();
}
