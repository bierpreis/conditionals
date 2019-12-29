package kb_creator.model.pairs;

import kb_creator.model.logic.PConditional;
import kb_creator.model.logic.KnowledgeBase;

import java.util.ArrayList;
import java.util.List;

//this class takes much less memory than the real list pair but its slower

public class CompressedPair extends AbstractPair {

    private long[][] compressedCandidatesArray; //todo tex


    public CompressedPair(KnowledgeBase knowledgeBase, List<PConditional> candidates) {
        this.knowledgeBase = knowledgeBase;

        long lastConditionalNumber = 0;

        List<List<Long>> temporaryList = new ArrayList<>();

        for (PConditional currentCandidate : candidates) {
            if (currentCandidate.getNumber() != lastConditionalNumber + 1) {

                //this should not be executed in the firstNumber iteration
                if (!temporaryList.isEmpty())
                    temporaryList.get(temporaryList.size() - 1).add(lastConditionalNumber);


                temporaryList.add(new ArrayList<>(2));
                temporaryList.get(temporaryList.size() - 1).add(currentCandidate.getNumber());

                lastConditionalNumber = currentCandidate.getNumber();
            } else {
                lastConditionalNumber++;
            }

        }

        if (!temporaryList.isEmpty())
            temporaryList.get(temporaryList.size() - 1).add(lastConditionalNumber);


        compressedCandidatesArray = new long[temporaryList.size()][2];
        for (int i = 0; i < temporaryList.size(); i++) {
            compressedCandidatesArray[i][0] = temporaryList.get(i).get(0);
            compressedCandidatesArray[i][1] = temporaryList.get(i).get(1);

        }

    }

    //this is intended to be used with real pairs as input but would work with compressed too
    //should be used by ram buffer to compress the real pairs from creator
    public CompressedPair(AbstractPair originalPair) {
        this.knowledgeBase = originalPair.getKnowledgeBase();

        long lastConditionalNumber = 0;

        List<List<Long>> temporaryList = new ArrayList<>();

        for (PConditional currentCandidate : originalPair.getCandidatesList()) {
            if (currentCandidate.getNumber() != lastConditionalNumber + 1) {

                //this should not be executed in the firstNumber iteration
                if (!temporaryList.isEmpty())
                    temporaryList.get(temporaryList.size() - 1).add(lastConditionalNumber);


                temporaryList.add(new ArrayList<>(2));
                temporaryList.get(temporaryList.size() - 1).add(currentCandidate.getNumber());

                lastConditionalNumber = currentCandidate.getNumber();
            } else {
                lastConditionalNumber++;
            }

        }

        if (!temporaryList.isEmpty())
            temporaryList.get(temporaryList.size() - 1).add(lastConditionalNumber);


        compressedCandidatesArray = new long[temporaryList.size()][2];
        for (int i = 0; i < temporaryList.size(); i++) {
            compressedCandidatesArray[i][0] = temporaryList.get(i).get(0);
            compressedCandidatesArray[i][1] = temporaryList.get(i).get(1);

        }

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

            arrayToReturn[counter][0] = Integer.parseInt(twoString[0]);

            arrayToReturn[counter][1] = Integer.parseInt(twoString[1]);


        }

        return arrayToReturn;
    }

    @Override
    public List<PConditional> getCandidatesList() {
        List<PConditional> candidatesList = new ArrayList<>();
        for (long[] candidateBounds : compressedCandidatesArray) {
            for (long i = candidateBounds[0]; i <= candidateBounds[1]; i++) {

                candidatesList.add(nfcMap.get(i));
            }
        }

        return candidatesList;
    }

    @Override
    public void clear() {
        knowledgeBase = null;
        compressedCandidatesArray = null;
    }


    //this method creates a file string compressed with the compression in this pair implementation
    //therefore the file is much shorter
    @Override
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("buffer");
        sb.append(knowledgeBase.getNumber());
        sb.append("\n\n");
        sb.append("KB\n");
        sb.append(knowledgeBase.toShortFileString());
        sb.append("\n\n");
        sb.append("candidates\n");
        //if this firstNumber entry is 0, there are no candidates

        int pairNumber = 0;

        if (compressedCandidatesArray.length == 0 || compressedCandidatesArray[0][0] == 0)
            sb.append("EMPTY");


        while (pairNumber < compressedCandidatesArray.length) {
            if (compressedCandidatesArray[pairNumber][0] != 0) {
                sb.append(compressedCandidatesArray[pairNumber][0]);
                sb.append("-");
                sb.append(compressedCandidatesArray[pairNumber][1]);
                if (pairNumber != (compressedCandidatesArray.length - 1))
                    sb.append(", ");
            }
            pairNumber++;
        }
        return sb.toString();
    }
}
