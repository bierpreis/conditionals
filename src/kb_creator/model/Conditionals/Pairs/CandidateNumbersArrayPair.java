package kb_creator.model.Conditionals.Pairs;

import kb_creator.model.Conditionals.KnowledgeBase.AbstractKnowledgeBase;
import kb_creator.model.Conditionals.KnowledgeBase.ObjectKnowledgeBase;
import kb_creator.model.Conditionals.NewConditional;

import java.util.ArrayList;
import java.util.List;


public class CandidateNumbersArrayPair extends AbstractPair {

    private int[] candidatesNumbersArray;

    //this class is much more memory efficient then candidateNumbrersListstPair
    public CandidateNumbersArrayPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;

        List<Integer> integerTempList = new ArrayList<>(candidates.size());

        for (NewConditional conditionalToAdd : candidates)
            integerTempList.add(conditionalToAdd.getNumber());

        candidatesNumbersArray = integerTempList.stream().mapToInt(i -> i).toArray();

    }

    public CandidateNumbersArrayPair(String stringFromFile) {

        String[] splitString = stringFromFile.split("candidates");
        if (splitString.length != 2)
            throw new RuntimeException("Invalid Candidate Pair File: " + splitString.length + "length");

        //divide string into kb and candidates
        //create both from subString
        knowledgeBase = new ObjectKnowledgeBase(splitString[0]);
        candidatesNumbersArray = createCandidatesArrayFromString(splitString[1]);
    }


    private int[] createCandidatesArrayFromString(String inputString) {//todo: when there are 0 candidates, then "" is the parameter and then scanning integers will fail
        inputString = inputString.replaceAll("\n", "");

        String[] candidatesStringArray = inputString.split(", ");
        for (String string : candidatesStringArray) {
            System.out.print(string + "");
        }
        System.out.println("");
        int[] candidatesArray = new int[candidatesStringArray.length];
        for (String candidateString : candidatesStringArray) {
            if (candidateString.length() == 0)
                System.out.println("!!! " + candidateString);
            candidateString.replace(", ", "");
        }
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
        sb.append("KB\n");
        sb.append(knowledgeBase.toShortFileString());
        sb.append("\n\n");
        sb.append("candidates\n");

        for (int i = 0; i < candidatesNumbersArray.length; i++) {
            sb.append(candidatesNumbersArray[i]);
            if (i != candidatesNumbersArray.length - 1)
                sb.append(", ");
        }
        return sb.toString();
    }

    @Override
    public void deleteCandidates() {
        candidatesNumbersArray = null;
    }
}
