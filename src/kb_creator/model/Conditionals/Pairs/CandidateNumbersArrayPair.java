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

        String[] splitString = stringFromFile.split("candidates\n");
        if (splitString.length != 2)
            throw new RuntimeException("Invalid Candidate Pair File: " + splitString.length + "length");

        //divide string into kb and candidates
        //create both from subString
        knowledgeBase = new ObjectKnowledgeBase(splitString[0]);

        candidatesNumbersArray = createCandidatesArrayFromString(splitString[1]);
    }


    private int[] createCandidatesArrayFromString(String inputString) {

        inputString = inputString.replaceAll("\n", "");
        if (inputString.length() < 2)
            System.out.println("candidate string: " + inputString);
        String[] candidatesStringArray = inputString.split(", ");
        int[] candidatesArray = new int[candidatesStringArray.length];
        for (String candidateString : candidatesStringArray) {
            candidateString.replace(", ", "");
        }
        //it will be "EMPTY" if there are no candidates
        if (!inputString.equals("EMPTY"))
            for (int i = 0; i < candidatesStringArray.length; i++) {
                if (candidatesStringArray[i].length() != 0)
                    candidatesArray[i] = Integer.parseInt(candidatesStringArray[i]);
            }
        if (candidatesArray[0] == 0)
            System.out.println("returned 0 !=!=!");
        return candidatesArray;
    }

    @Override
    public List<NewConditional> getCandidatesList() {
        //System.out.println("found: " + candidatesNumbersList);
        List<NewConditional> candidatesList = new ArrayList<>();

        for (int candidateNumber : this.candidatesNumbersArray) {

            //todo: candidate number can be 0. why is that? it should not be!
            if (candidateNumber != 0)
                candidatesList.add(nfcMap.get(candidateNumber));
            else System.out.println("length of array with element 0: " + candidatesNumbersArray.length);
        }


        if (candidatesList.contains(null))
            System.out.println("null in there!");
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
        if (candidatesNumbersArray.length > 0) {
            for (int i = 0; i < candidatesNumbersArray.length; i++) {
                sb.append(candidatesNumbersArray[i]);
                if (i != candidatesNumbersArray.length - 1)
                    sb.append(", ");
            }
        } else sb.append("EMPTY");
        return sb.toString();
    }

    @Override
    public void deleteCandidates() {
        candidatesNumbersArray = null;
    }
}
