package kb_creator.model.conditionals.pairs;

import kb_creator.model.conditionals.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.conditionals.knowledge_base.ObjectKnowledgeBase;
import kb_creator.model.conditionals.NewConditional;

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
            throw new RuntimeException("Invalid Candidate Pair File: " + splitString.length + "\n" + splitString[0] + "!!");

        //divide string into kb and candidates
        //create both from subString
        knowledgeBase = new ObjectKnowledgeBase(splitString[0]);

        candidatesNumbersArray = createCandidatesArrayFromString(splitString[1]);
    }


    private int[] createCandidatesArrayFromString(String inputString) {

        inputString = inputString.replaceAll("\n", "");

        String[] candidatesStringArray = inputString.split(", ");
        int[] candidatesArray = new int[candidatesStringArray.length];
        for (String candidateString : candidatesStringArray) {
            candidateString.replace(", ", "");
        }
        //it will be "EMPTY" if there are no candidates
        //then as java standard the array will contain int[0] = 0
        if (!inputString.equals("EMPTY"))
            for (int i = 0; i < candidatesStringArray.length; i++) {
                if (candidatesStringArray[i].length() != 0)
                    candidatesArray[i] = Integer.parseInt(candidatesStringArray[i]);
            }


        return candidatesArray;
    }

    @Override
    public List<NewConditional> getCandidatesList() {
        //System.out.println("found: " + candidatesNumbersList);
        List<NewConditional> candidatesList = new ArrayList<>();

        for (int candidateNumber : this.candidatesNumbersArray) {

            //candidate numbers is 0 when there are 0 candidates
            if (candidateNumber != 0)
                candidatesList.add(nfcMap.get(candidateNumber));
        }


        if (candidatesList.contains(null))
            System.out.println("null in there!");
        return candidatesList;
    }

    //todo: this is shit
    @Override
    public String toString() {
        return null;
    }

    @Override
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("pair_lists");
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
