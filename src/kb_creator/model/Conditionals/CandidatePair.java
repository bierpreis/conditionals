package kb_creator.model.Conditionals;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CandidatePair {
    private KnowledgeBase knowledgeBase;
    private List<Integer> candidatesNumbersList;

    private static Map<Integer, NewConditional> nfcMap;


    public CandidatePair(KnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;
        candidatesNumbersList = new ArrayList<>();

        for (NewConditional conditionalToAdd : candidates) {
            this.candidatesNumbersList.add(conditionalToAdd.getNumber());
        }
    }

    //todo: only use this when really needed. maybe delete?
    public List<NewConditional> getCandidatesList() {
        List<NewConditional> candidatesList = new ArrayList<>();
        for (Integer candidateNumber : this.candidatesNumbersList) {
            candidatesList.add(nfcMap.get(candidateNumber));
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

    public static void setNfc(Map<Integer, NewConditional> nfc) {
        nfcMap = nfc;
    }

    public String toShortString() {
        return "CP: <" + knowledgeBase.getSize() + ", " + candidatesNumbersList.size();
    }

    public int getNumber() {
        return knowledgeBase.getKbNumber();
    }

    //todo: candidatesNumbersList is not ordered. why?
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CandidatePair");
        sb.append(knowledgeBase.getKbNumber());
        sb.append("\n\n");
        sb.append("KB:\n");
        sb.append(knowledgeBase.newToFileString());
        sb.append("\n\n");
        sb.append("Candidates:\n");
        sb.append(candidatesNumbersList);
        sb.append("\n\nEOF");

        return sb.toString();
    }
}
