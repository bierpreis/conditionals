package kb_creator.model;

import nfc.model.Conditional;

import java.util.List;

public class KnowledgeBase {
    private List<Conditional> conditionalList;

    public KnowledgeBase() {
    }

    public boolean checkConsistency(Conditional conditional) {
        //todo: here concistency test
        return true;
    }

    public void add(Conditional conditional) {
        conditionalList.add(conditional);
        //todo: sort list?
    }

    public List<Conditional> getConditionalList() {
        return conditionalList;
    }
}
