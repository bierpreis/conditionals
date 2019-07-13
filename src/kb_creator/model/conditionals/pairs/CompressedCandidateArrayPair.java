package kb_creator.model.conditionals.pairs;

import kb_creator.model.conditionals.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.conditionals.NewConditional;
import kb_creator.model.conditionals.knowledge_base.ObjectKnowledgeBase;

import java.util.ArrayList;
import java.util.List;

public class CompressedCandidateArrayPair extends AbstractPair {

    private int[][] compressedCandidatesArray;


    public CompressedCandidateArrayPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;

        compressedCandidatesArray = new int[7][2];

        int lastConditionalNumber = 0;
        int nextArrayNumber = 0;


        for (NewConditional currentCandidate : candidates) {
            if (currentCandidate.getNumber() != lastConditionalNumber + 1) {
                compressedCandidatesArray[nextArrayNumber][0] = currentCandidate.getNumber();
                compressedCandidatesArray[nextArrayNumber][1] = currentCandidate.getNumber();

                nextArrayNumber = nextArrayNumber + 1;
                lastConditionalNumber = currentCandidate.getNumber();
            } else {
                compressedCandidatesArray[nextArrayNumber - 1][1] = currentCandidate.getNumber();
                lastConditionalNumber++;
            }

        }

    }

    public CompressedCandidateArrayPair(String stringFromFile) {
        String[] splitString = stringFromFile.split("candidates\n");
        if (splitString.length != 2)
            throw new RuntimeException("Invalid Candidate Pair File: " + splitString.length + "\n" + splitString[0] + "!!");

        //divide string into kb and candidates
        //create both from subString
        knowledgeBase = new ObjectKnowledgeBase(splitString[0]);
        compressedCandidatesArray = createCandidatesArrayFromString(splitString[1]);
    }

    private int[][] createCandidatesArrayFromString(String stringFromFile) {
        String[] stringArray = stringFromFile.split(", ");
        int[][] arrayToReturn = new int[stringArray.length][2];
        int counter = 0;


        for (String string : stringArray) {
            string = string.replaceAll("\n", "");

            if (string.equals("EMPTY")) {
                arrayToReturn[0][0] = 0;
                arrayToReturn[0][1] = 0;
                return arrayToReturn;
            }
            String[] twoString = string.split("-");


            if (twoString.length != 2)
                throw new RuntimeException("Invalid compressed candidates String: " + stringFromFile);


            //sometimes the is a line break after last number. remove it because parsing int would fail otherwise.

            arrayToReturn[counter][0] = Integer.parseInt(twoString[0]);

            arrayToReturn[counter][1] = Integer.parseInt(twoString[1]);


        }

        return arrayToReturn;
    }

    @Override
    public List<NewConditional> getCandidatesList() {
        List<NewConditional> candidatesList = new ArrayList<>();
        for (int[] candidateBounds : compressedCandidatesArray) {
            //if it is 0, all candidates are found
            if (candidateBounds[0] == 0) {
                return candidatesList;
            }
            for (int i = candidateBounds[0]; i <= candidateBounds[1]; i++) {

                candidatesList.add(nfcMap.get(i));
            }
        }

        return candidatesList;
    }

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
        compressedCandidatesArray = null;
    }

    public String toFileString() {
        return toShortFileString();
    }

    private String toLongFileString() {
        List<NewConditional> candidatesList = getCandidatesList();
        StringBuilder sb = new StringBuilder();
        sb.append("buffer");
        sb.append(knowledgeBase.getKbNumber());
        sb.append("\n\n");
        sb.append("KB\n");
        sb.append(knowledgeBase.toShortFileString());
        sb.append("\n\n");
        sb.append("candidates\n");
        if (candidatesList.size() > 0) {
            for (int i = 0; i < candidatesList.size(); i++) {
                sb.append(candidatesList.get(i).getNumber());
                if (i != candidatesList.size() - 1)
                    sb.append(", ");
            }
        } else sb.append("EMPTY");
        return sb.toString();
    }

    //this method creates a file string complressed with the compression in this pair implementation
    //therefore the file is much shorter
    private String toShortFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("buffer");
        sb.append(knowledgeBase.getKbNumber());
        sb.append("\n\n");
        sb.append("KB\n");
        sb.append(knowledgeBase.toShortFileString());
        sb.append("\n\n");
        sb.append("candidates\n");
        //if this first entry is 0, there are no candidates

        int pairNumber = 0;

        if (compressedCandidatesArray.length == 0 || compressedCandidatesArray[0][0] == 0)
            sb.append("EMPTY");


        while (pairNumber < compressedCandidatesArray.length) {
            if (compressedCandidatesArray[pairNumber][0] != 0) {
                sb.append(compressedCandidatesArray[pairNumber][0]);
                sb.append("-");
                sb.append(compressedCandidatesArray[pairNumber][1]);
                sb.append(", ");
            }
            pairNumber++;
        }

        return sb.toString().replaceAll(", $", "");
    }
}
