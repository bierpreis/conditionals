package kb_creator.model.pairs;

import kb_creator.model.logic.PConditional;
import kb_creator.model.logic.KnowledgeBase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RealPair extends AbstractPair {
    private List<PConditional> candidatesList;

    private static final Pattern NEW_LINE_PATTERN = Pattern.compile("\n");
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");
    private static final Pattern DASH_PATTERN = Pattern.compile("-");
    private static final Pattern CANDIDATES_NEWLINE_PATTERN = Pattern.compile("C\n");

    public RealPair(KnowledgeBase knowledgeBase, List<PConditional> candidates) {
        this.knowledgeBase = knowledgeBase;
        this.candidatesList = candidates;
    }

    //this is intended to be used with compressed pairs as input but will work with real pairs too
    //it will be used by ram buffer to decompress compressed pairs to save time for creator thread
    public RealPair(AbstractPair originalPair) {
        this.knowledgeBase = originalPair.getKnowledgeBase();
        this.candidatesList = originalPair.getCandidatesList();
    }

    public RealPair(String stringFromFile) {

        //divide string into kb and candidates
        String[] splitString = CANDIDATES_NEWLINE_PATTERN.split(stringFromFile);

        if (splitString.length != 2)
            throw new RuntimeException("Invalid Candidate Pair File: " + splitString.length + "\n" + splitString[0] + "!!");


        //create both from subString
        //long startTime = System.currentTimeMillis();
        knowledgeBase = new KnowledgeBase(splitString[0]);
        //System.out.println("time + " + (System.currentTimeMillis() - startTime));
        candidatesList = createCandidatesListFromString(splitString[1]);
    }

    //this method takes 0-1 ms so pre compiling regexes may be useless
    private List<PConditional> createCandidatesListFromString(String stringFromFile) {

        List<PConditional> listToReturn = new ArrayList<>();

        String[] stringArray = COMMA_PATTERN.split(stringFromFile);

        for (String string : stringArray) {
            string = NEW_LINE_PATTERN.matcher(string).replaceAll("");

            String[] twoString = DASH_PATTERN.split(string);


            //this will return empty list if there are no candidates
            if (twoString[0].equals("EMPTY"))
                return listToReturn;

            if (twoString.length != 2)
                throw new RuntimeException("Invalid compressed candidates String: " + stringFromFile);


            long firstNumber = Long.parseLong(twoString[0]);

            long secondNumber = Long.parseLong(twoString[1]);

            for (long i = firstNumber; i <= secondNumber; i++) {
                listToReturn.add(nfcMap.get(i));
            }


        }

        return listToReturn;
    }

    @Override
    public List<PConditional> getCandidatesList() {
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
    public void clear() {
        candidatesList = null;
        knowledgeBase = null;
    }


}

