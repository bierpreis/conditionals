package kb_creator.model.Conditionals;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CandidatePair {
    private KnowledgeBase knowledgeBase;
    private List<Integer> candidatesNumbersList;

    private static Map<Integer, NewConditional> conditionalMap;

    public CandidatePair(KnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;
        candidatesNumbersList = new ArrayList<>();

        //todo: maybe array?
        for (NewConditional conditionalToAdd : candidates) {
            this.candidatesNumbersList.add(conditionalToAdd.getNumber());
        }
    }

    //todo: only use this when really needed. maybe delete?
    public List<NewConditional> getCandidatesList() {
        List<NewConditional> candidatesList = new ArrayList<>();
        for (Integer candidateNumber : this.candidatesNumbersList) {
            candidatesList.add(conditionalMap.get(candidateNumber));
        }

        return candidatesList;
    }

    public List<Integer> getCandidatesNumbersList() {
        return candidatesNumbersList;
    }

    public KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    public String toString() {
        return "<" + knowledgeBase + ", " + candidatesNumbersList + ">\n";
    }

    public void deleteCandidates() {
        candidatesNumbersList = null;
    }

    public void deleteKB() {
        knowledgeBase = null;
    }

    //todo: what is this?
    public void setConditionalMap(Map<Integer, NewConditional> conditionalMap) {
        this.conditionalMap = conditionalMap;
    }

    public static void setNfc(Map<Integer, NewConditional> nfc) {
        conditionalMap = nfc;
    }

    public String toShortString() {
        return "CP: <" + knowledgeBase.getSize() + ", " + candidatesNumbersList.size();
    }
}
