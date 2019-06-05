package kb_creator.model.Conditionals;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//todo: abstract cp. one with real and one with numbers.
public class CandidatePair {
    private KnowledgeBase knowledgeBase;
    private List<Integer> candidatesNumbersList;

    private static Map<Integer, NewConditional> nfcMap;


    public CandidatePair(KnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;
        candidatesNumbersList = new ArrayList<>();

        for (NewConditional conditionalToAdd : candidates) {
            this.candidatesNumbersList.add(conditionalToAdd.getNumber());
            //System.out.println("added: " + candidatesNumbersList);
        }
    }

    public CandidatePair(String stringFromFile) {
        //todo
        String[] splitString = stringFromFile.split("candidates:");
        if (splitString.length != 2)
            throw new RuntimeException("Invalid Candidate Pair File");
        //divide string into kb and candidates
        //create both from subString
        knowledgeBase = new KnowledgeBase(splitString[0]);
        candidatesNumbersList = createCandidatesListFromString(splitString[1]);
    }

    //todo: test
    private List<Integer> createCandidatesListFromString(String inputString) {
        List<Integer> candidatesList = new ArrayList<>();
        String[] candidatesStringArray = inputString.split(",");

        for (String candidateString : candidatesStringArray)
            candidatesList.add(Integer.parseInt(candidateString));

        return candidatesList;
    }

    public List<NewConditional> getCandidatesList() {
        //System.out.println("found: " + candidatesNumbersList);
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

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lists");
        sb.append(knowledgeBase.getKbNumber());
        sb.append("\n\n");
        sb.append("KB:\n");
        sb.append(knowledgeBase.toShortFileString());
        sb.append("\n\n");
        sb.append("candidates:\n");
        sb.append(candidatesNumbersList);
        sb.append("\n\nEOF");

        return sb.toString();
    }
}
