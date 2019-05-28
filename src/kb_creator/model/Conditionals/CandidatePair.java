package kb_creator.model.Conditionals;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//todo: here conditionals map
public class CandidatePair {
    private KnowledgeBase knowledgeBase;
    private List<Integer> candidates;

    private static Map<Integer, NewConditional> conditionalMap;

    public CandidatePair(KnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;
        candidates = new LinkedList<>();

        for (NewConditional conditionalToAdd : candidates) {
            this.candidates.add(conditionalToAdd.getNumber());
        }
    }

    //todo: only use this when really needed. maybe delete?
    public List<NewConditional> getCandidates() {
        List<NewConditional> candidatesList = new LinkedList<>();
        for (Integer candidateNumber : candidates) {
            candidatesList.add(conditionalMap.get(candidateNumber));
        }

        return candidatesList;
    }

    public List<Integer> getCandidatesNumbers() {
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

    public void setConditionalMap(Map<Integer, NewConditional> conditionalMap) {
        this.conditionalMap = conditionalMap;
    }

    public static void setNfc(Map<Integer, NewConditional> nfc) {
        conditionalMap = nfc;
    }
}
