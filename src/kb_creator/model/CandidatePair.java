package kb_creator.model;

import nfc.model.Conditional;

import java.util.List;

public class CandidatePair {
    private List<Conditional> knowledgeBase;
    private List<Conditional> candidates;

    public CandidatePair(List<Conditional> knowledgeBase, List<Conditional> candidates) {
        this.knowledgeBase = knowledgeBase;
        this.candidates = candidates;
    }
}
