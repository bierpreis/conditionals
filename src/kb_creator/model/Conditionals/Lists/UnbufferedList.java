package kb_creator.model.Conditionals.Lists;

import kb_creator.model.Conditionals.CandidatePair;

import java.util.ArrayList;
import java.util.List;

public class UnbufferedList {
    List<List<CandidatePair>> cpList;

    public UnbufferedList() {
        cpList = new ArrayList<>();
    }

    public List<List<CandidatePair>> getList() {
        return cpList;
    }
}
