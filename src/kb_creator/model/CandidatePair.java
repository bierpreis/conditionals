package kb_creator.model;

import nfc.model.Conditional;

import java.util.List;

public class CandidatePair {
    private KnowledgeBase knowledgeBase;
    private List<Conditional> candidates;

    public CandidatePair(KnowledgeBase knowledgeBase, List<Conditional> candidates) {
        this.knowledgeBase = knowledgeBase;
        this.candidates = candidates;
    }

    public List<Conditional> getCandidates() {
        return candidates;
    }

    public KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }
}
