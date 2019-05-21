package kb_creator.model.Conditionals;

import java.util.List;

public class CandidatePair {
    private KnowledgeBase knowledgeBase;
    private List<NewConditional> candidates;

    public CandidatePair(KnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;
        this.candidates = candidates;
    }

    public List<NewConditional> getCandidates() {
        return candidates;
    }

    public KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    public String toString() {
        return "<" + knowledgeBase + ", " + candidates + ">\n";
    }

    public void deleteCandidates() {
        candidates = null;
    }

    public void deleteKB() {
        knowledgeBase = null;
    }
}
