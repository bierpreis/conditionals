package kb_creator.model.pairs;

import kb_creator.model.propositional_logic.NewConditional;
import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.knowledge_base.ObjectKnowledgeBase;

import java.util.ArrayList;
import java.util.List;

public class RealListPair extends AbstractPair {
    private List<NewConditional> candidatesList;

    //todo: think about everything in here. this class is very important!
    public RealListPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;

        this.candidatesList = candidates;
    }

    public RealListPair(String stringFromFile) {

        //divide string into kb and candidates
        String[] splitString = stringFromFile.split("candidates\n");

        if (splitString.length != 2)
            throw new RuntimeException("Invalid Candidate Pair File: " + splitString.length + "\n" + splitString[0] + "!!");


        //create both from subString
        //long startTime = System.currentTimeMillis();
        knowledgeBase = new ObjectKnowledgeBase(splitString[0]);
        //System.out.println("time + " + (System.currentTimeMillis() - startTime));
        candidatesList = createCandidatesListFromString(splitString[1]);
    }

    private List<NewConditional> createCandidatesListFromString(String stringFromFile) {

        List<NewConditional> listToReturn = new ArrayList<>();

        String[] stringArray = stringFromFile.split(", ");

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


    @Override
    public String toString() {
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


        List<NumberPair> numberPairList = new ArrayList<>();

        if (candidatesList.isEmpty())
            sb.append("EMPTY");
        else {
            int lastConditionalNumber = candidatesList.get(0).getNumber() - 1;

            numberPairList.add(new NumberPair(candidatesList.get(0).getNumber()));

            for (NewConditional currentCandidate : candidatesList) {
                if (currentCandidate.getNumber() == lastConditionalNumber + 1)
                    lastConditionalNumber = currentCandidate.getNumber();
                else {
                    numberPairList.get(numberPairList.size() - 1).setSecond(lastConditionalNumber);

                    numberPairList.add(new NumberPair(currentCandidate.getNumber()));

                    lastConditionalNumber = currentCandidate.getNumber();

                }

            }
            numberPairList.get(numberPairList.size() - 1).setSecond(lastConditionalNumber);
        }

        for (NumberPair numberPair : numberPairList) {
            sb.append(numberPair.toString());
            sb.append(", ");
        }

        return sb.toString().replaceAll(", $", "");
    }

    class NumberPair {
        int first, second;

        public NumberPair(int first) {
            this.first = first;
        }


        public void setSecond(int second) {
            this.second = second;
        }

        @Override
        public String toString() {
            return first + "-" + second;
        }
    }
}
