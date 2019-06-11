package kb_creator.model.Conditionals.Pairs;

import kb_creator.model.Conditionals.KnowledgeBase.AbstractKnowledgeBase;
import kb_creator.model.Conditionals.KnowledgeBase.ObjectKnowledgeBase;
import kb_creator.model.Conditionals.NewConditional;

import java.util.ArrayList;
import java.util.List;

//todo this class
public class CandidateNumbersArrayPair extends AbstractPair {

    private int[] candidatesNumbersArray;

    public CandidateNumbersArrayPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;

        //todo: remove
        if (knowledgeBase == null)
            System.out.println("init kb with null!!!");

        //todo: this must be wrong
        candidatesNumbersArray = candidates.stream().mapToInt(conditionalToAdd -> getNumber()).toArray();

    }

    public CandidateNumbersArrayPair(String stringFromFile) {
        //todo
        String[] splitString = stringFromFile.split("candidates:");
        if (splitString.length != 2)
            throw new RuntimeException("Invalid Candidate Pair File");

        //divide string into kb and candidates
        //create both from subString
        knowledgeBase = new ObjectKnowledgeBase(splitString[0]);
        candidatesNumbersArray = createCandidatesArrayFromString(splitString[1]);
    }

    //todo: test
    private int[] createCandidatesArrayFromString(String inputString) {
        int[] candidatesArray = new int[inputString.length()];
        String[] candidatesStringArray = inputString.split(", ");

        for (String candidateString : candidatesStringArray)
            candidateString.replace(", ", "");

        for (int i = 0; i < candidatesStringArray.length; i++) {

            candidatesArray[i] = Integer.parseInt(candidatesStringArray[i]);
        }
        return candidatesArray;
    }

    @Override
    public List<NewConditional> getCandidatesList() {
        //System.out.println("found: " + candidatesNumbersList);
        List<NewConditional> candidatesList = new ArrayList<>();
        for (int candidateNumber : this.candidatesNumbersArray) {
            candidatesList.add(nfcMap.get(candidateNumber));
        }

        return candidatesList;
    }


    @Override
    public String toString() {
        return null;
    }


    @Override
    public String toShortString() {
        return null;
    }


    @Override
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lists");
        sb.append(knowledgeBase.getKbNumber());
        sb.append("\n\n");
        sb.append("KB:\n");
        sb.append(knowledgeBase.toShortFileString());
        sb.append("\n\n");
        sb.append("candidates:\n");

        for (int candidateNumber : candidatesNumbersArray) {
            sb.append(candidateNumber);
            sb.append(", ");
        }
        sb.append("\n\nEOF");

        return sb.toString().replace(",$", "");
    }

    @Override
    public void deleteCandidates() {
        candidatesNumbersArray = null;
    }
}
