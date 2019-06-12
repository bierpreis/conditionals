package kb_creator.model.Conditionals.Pairs;


import kb_creator.model.Conditionals.KnowledgeBase.AbstractKnowledgeBase;
import kb_creator.model.Conditionals.KnowledgeBase.ObjectKnowledgeBase;
import kb_creator.model.Conditionals.NewConditional;

import java.util.ArrayList;
import java.util.List;


public class CandidateNumbersListPair extends AbstractPair {
    private AbstractKnowledgeBase knowledgeBase;
    private List<Integer> candidatesNumbersList;

    public CandidateNumbersListPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;
        candidatesNumbersList = new ArrayList<>();

        for (NewConditional conditionalToAdd : candidates) {
            this.candidatesNumbersList.add(conditionalToAdd.getNumber());
            //System.out.println("added: " + candidatesNumbersList);
        }
    }

    public CandidateNumbersListPair(String stringFromFile) {
        //todo
        String[] splitString = stringFromFile.split("candidates:");
        if (splitString.length != 2)
            throw new RuntimeException("Invalid Candidate Pair File");
        //divide string into kb and candidates
        //create both from subString
        knowledgeBase = new ObjectKnowledgeBase(splitString[0]);
        candidatesNumbersList = createCandidatesListFromString(splitString[1]);
    }

    //todo: test
    private List<Integer> createCandidatesListFromString(String inputString) {
        List<Integer> candidatesList = new ArrayList<>();
        String[] candidatesStringArray = inputString.split(", ");

        for (String candidateString : candidatesStringArray)
            candidatesList.add(Integer.parseInt(candidateString));

        return candidatesList;
    }

    @Override
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

    @Override
    public String toString() {
        return "<" + knowledgeBase + ", " + candidatesNumbersList + ">\n";
    }


    @Override
    public AbstractKnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    @Override
    public String toShortString() {
        return "CP: <" + knowledgeBase.getSize() + ", " + candidatesNumbersList.size();
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
        sb.append(candidatesNumbersList);
        sb.append("\n\nEOF");

        return sb.toString();
    }

    @Override
    public void deleteCandidates() {
        candidatesNumbersList = null;
    }
}