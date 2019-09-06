package kb_creator.model.pairs;

import kb_creator.model.propositional_logic.NewConditional;
import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.knowledge_base.ObjectKnowledgeBase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RealListPair extends AbstractPair {
    private List<NewConditional> candidatesList;

    private static final Pattern NEW_LINE_PATTERN = Pattern.compile("\n");
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");
    private static final Pattern DASH_PATTERN = Pattern.compile("-");
    private static final Pattern CANDIDATES_NEWLINE_PATTERN = Pattern.compile("candidates\n");

    public RealListPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;

        this.candidatesList = candidates;
    }

    public RealListPair(String stringFromFile) {

        //divide string into kb and candidates
        String[] splitString = CANDIDATES_NEWLINE_PATTERN.split(stringFromFile);

        if (splitString.length != 2)
            throw new RuntimeException("Invalid Candidate Pair File: " + splitString.length + "\n" + splitString[0] + "!!");


        //create both from subString
        //long startTime = System.currentTimeMillis();
        knowledgeBase = new ObjectKnowledgeBase(splitString[0]);
        //System.out.println("time + " + (System.currentTimeMillis() - startTime));
        candidatesList = createCandidatesListFromString(splitString[1]);
    }

    //this method takes 0-1 ms so pre compiling regexes may be useless
    private List<NewConditional> createCandidatesListFromString(String stringFromFile) {

        List<NewConditional> listToReturn = new ArrayList<>();

        String[] stringArray = COMMA_PATTERN.split(stringFromFile);

        for (String string : stringArray) {
            string = NEW_LINE_PATTERN.matcher(string).replaceAll("");

            String[] twoString = DASH_PATTERN.split(string);


            //this will return empty list if there are no candidates
            if (twoString[0].equals("EMPTY"))
                return listToReturn;

            if (twoString.length != 2)
                throw new RuntimeException("Invalid compressed candidates String: " + stringFromFile);


            int firstNumber = Integer.parseInt(twoString[0]);

            int secondNumber = Integer.parseInt(twoString[1]);

            for (int i = firstNumber; i <= secondNumber; i++) {
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


    //this method creates a file string compressed with the compression in this pair implementation
    //therefore the file is much shorter
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("buffer"); //todo: short to buf?
        sb.append(knowledgeBase.getKbNumber());
        sb.append("\nKB\n");
        sb.append(knowledgeBase.toShortFileString());
        sb.append("\ncandidates\n");
        //if this firstNumber entry is 0, there are no candidates


        List<CandidateGroup> candidateGroupList = new ArrayList<>();

        if (candidatesList.isEmpty())
            sb.append("EMPTY");
        else {
            int lastConditionalNumber = candidatesList.get(0).getNumber() - 1;

            candidateGroupList.add(new CandidateGroup(candidatesList.get(0).getNumber()));

            for (NewConditional currentCandidate : candidatesList) {
                if (currentCandidate.getNumber() == lastConditionalNumber + 1)
                    lastConditionalNumber = currentCandidate.getNumber();
                else {
                    candidateGroupList.get(candidateGroupList.size() - 1).setLastNumber(lastConditionalNumber);

                    candidateGroupList.add(new CandidateGroup(currentCandidate.getNumber()));

                    lastConditionalNumber = currentCandidate.getNumber();

                }

            }
            candidateGroupList.get(candidateGroupList.size() - 1).setLastNumber(lastConditionalNumber);
        }

        for (int i = 0; i < candidateGroupList.size(); i++) {
            sb.append(candidateGroupList.get(i).toString());
            if (i != (candidateGroupList.size() - 1))
                sb.append(",");
        }

        return sb.toString();
    }

    @Override
    public void clear() {
        candidatesList = null;
        knowledgeBase = null;
    }

    class CandidateGroup {
        int firstNumber, lastNumber;

        public CandidateGroup(int firstNumber) {
            this.firstNumber = firstNumber;
        }


        public void setLastNumber(int lastNumber) {
            this.lastNumber = lastNumber;
        }

        @Override
        public String toString() {
            return firstNumber + "-" + lastNumber;
        }
    }
}
