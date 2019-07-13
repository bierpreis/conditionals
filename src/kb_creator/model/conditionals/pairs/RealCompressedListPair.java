package kb_creator.model.conditionals.pairs;

import kb_creator.model.conditionals.NewConditional;
import kb_creator.model.conditionals.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.conditionals.knowledge_base.ObjectKnowledgeBase;

import java.util.ArrayList;
import java.util.List;

public class RealCompressedListPair extends AbstractPair {
    private List<NewConditional> candidatesList;

    //todo: this class


    public RealCompressedListPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;

        this.candidatesList = candidates;

        int lastConditionalNumber = 0;
        int nextArrayNumber = 0;


    }

    public RealCompressedListPair(String stringFromFile) {
        String[] splitString = stringFromFile.split("candidates\n");
        if (splitString.length != 2)
            throw new RuntimeException("Invalid Candidate Pair File: " + splitString.length + "\n" + splitString[0] + "!!");

        //divide string into kb and candidates
        //create both from subString
        knowledgeBase = new ObjectKnowledgeBase(splitString[0]);
        candidatesList = createCandidatesListFromString(splitString[1]);
    }

    private List<NewConditional> createCandidatesListFromString(String stringFromFile) {
        String[] stringArray = stringFromFile.split(", ");
        List<NewConditional> listToReturn = new ArrayList<>();

        for (String string : stringArray) {
            string = string.replaceAll("\n", "");

            //return empty list if there are no candidates
            if (string.equals("EMPTY"))
                return listToReturn;


            String[] twoString = string.split("-");


            if (twoString.length != 2)
                throw new RuntimeException("Invalid compressed candidates String: " + stringFromFile);


            int firstNumer = Integer.parseInt(twoString[0]);

            int secondNumer = Integer.parseInt(twoString[1]);

            for (int i = firstNumer; i <= secondNumer; i++) {
                listToReturn.add(nfcMap.get(i));
            }


        }

        return listToReturn;
    }

    @Override
    public List<NewConditional> getCandidatesList() {
        return candidatesList;
    }

    //todo: is this needed?
    @Override
    public String toString() {
        List<NewConditional> candidatesList = getCandidatesList();
        StringBuilder sb = new StringBuilder();
        sb.append("<(");
        sb.append(knowledgeBase.toShortFileString());
        sb.append("), (");
        if (candidatesList.size() > 0) {
            for (int i = 0; i < candidatesList.size(); i++) {
                sb.append(candidatesList.get(i).getNumber());
                if (i != candidatesList.size() - 1)
                    sb.append(", ");
            }
        } else sb.append("EMPTY");
        sb.append(")>");
        return sb.toString();
    }

    @Override
    public void deleteCandidates() {
        candidatesList = null;
    }

    //todo: create short string from candidatesList
    //this method creates a file string complressed with the compression in this pair implementation
    //therefore the file is much shorter
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("buffer");
        sb.append(knowledgeBase.getKbNumber());
        sb.append("\n\n");
        sb.append("KB\n");
        sb.append(knowledgeBase.toShortFileString());
        sb.append("\n\n");
        sb.append("candidates\n");
        //if this first entry is 0, there are no candidates

        int firstConditionalNumberToAdd = 0;
        int lastConditionalNumber = 0;

        if (candidatesList.isEmpty())
            sb.append("EMPTY");
        else
            for (NewConditional currentCandidate : candidatesList) {
                if (currentCandidate.getNumber() != lastConditionalNumber + 1) { //todo: switch to ==
                    sb.append(firstConditionalNumberToAdd);
                    sb.append("-");
                    sb.append(currentCandidate.getNumber());
                    sb.append(", ");
                    lastConditionalNumber = currentCandidate.getNumber();
                } else {
                    lastConditionalNumber++;
                }

            }
        return sb.toString().replaceAll(", $", "");
    }

    class NumberPair {
        int first, second;

        public NumberPair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public int getFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }
    }
}
