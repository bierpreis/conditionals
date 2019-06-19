package kb_creator.model.Conditionals.Pairs;

import kb_creator.model.Conditionals.KnowledgeBase.AbstractKnowledgeBase;
import kb_creator.model.Conditionals.NewConditional;

import java.util.ArrayList;
import java.util.List;

public class CompressedCandidateArrayPair extends AbstractPair {
    private int[][] compressedCandidatesArray;

    //todo: test if this is really correct
    public CompressedCandidateArrayPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;

        compressedCandidatesArray = new int[7][2];

        int lastConditionalNumber = 0;
        int nextArrayNumber = 0;

        //todo: what happens in first case? else will trigger and it would fail. debug!
        for (NewConditional conditional : candidates) {
            if (conditional.getNumber() != lastConditionalNumber + 1) {
                compressedCandidatesArray[nextArrayNumber][0] = conditional.getNumber();
                compressedCandidatesArray[nextArrayNumber][1] = conditional.getNumber();

                nextArrayNumber = nextArrayNumber + 1;
                lastConditionalNumber = conditional.getNumber();
            } else {
                compressedCandidatesArray[nextArrayNumber - 1][1] = conditional.getNumber();
                lastConditionalNumber++;
            }

        }

    }

    @Override
    public List<NewConditional> getCandidatesList() {
        //todo test
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
        return null;
    }

    @Override
    public void deleteCandidates() {
        compressedCandidatesArray = null;
    }

    @Override
    public String toShortString() {
        return null;
    }


    //this method creates a file string in the exacly same format like the other pair implementations
    @Override
    public String toFileString() {
        List<NewConditional> candidatesList = getCandidatesList();
        StringBuilder sb = new StringBuilder();
        sb.append("Lists");
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
    public String toShortFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lists");
        sb.append(knowledgeBase.getKbNumber());
        sb.append("\n\n");
        sb.append("KB\n");
        sb.append(knowledgeBase.toShortFileString());
        sb.append("\n\n");
        sb.append("candidates\n");
        //if this first entry is 0, there are no candidates
        if (compressedCandidatesArray[0][0] != 0) {
            sb.append(compressedCandidatesArray[0][0]);
            sb.append("-");
            sb.append(compressedCandidatesArray[0][1]);

            //then check if there is a second group to add
            if (compressedCandidatesArray[1][0] != 0) {
                sb.append(compressedCandidatesArray[1][0]);
                sb.append("-");
                sb.append(compressedCandidatesArray[1][1]);
            }
        } else sb.append("EMPTY");
        return sb.toString();
    }
}
