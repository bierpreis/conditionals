package kb_creator.model.Conditionals;

import java.util.List;

public class CandidatePair {
    private NewKnowledgeBase knowledgeBase;
    private List<NewConditional> candidates;

    public CandidatePair(NewKnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;
        this.candidates = candidates;
    }

    public List<NewConditional> getCandidates() {
        return candidates;
    }

    public NewKnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    public String toString() {
        return "<" + knowledgeBase + ", " + candidates + ">\n";
    }
}
