package kb_creator.model.pairs;

import kb_creator.model.logic.KnowledgeBase;
import kb_creator.model.logic.PConditional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractPair {

    protected KnowledgeBase knowledgeBase;
    protected static Map<Long, PConditional> nfcMap;

    //concrete methods
    public KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    public static void setNfc(Map<Long, PConditional> nfc) {
        nfcMap = nfc;
    }


    //abstract methods

    public abstract List<PConditional> getCandidatesList();

    public abstract void clear();

    //this is actually just for debug purposes
    public String toString() {
        List<PConditional> candidatesList = getCandidatesList();
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

    //this compression makes the file much shorter than simply writing all the numbers could be

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("KB\n");
        sb.append(knowledgeBase.toShortFileString());
        sb.append("\nC\n");

        List<PConditional> candidatesList = getCandidatesList();
        if (!candidatesList.isEmpty()) {

            //init the first candidate in list
            List<CandidateGroup> candidateGroupList = new ArrayList<>();
            long lastConditionalNumber = candidatesList.get(0).getNumber() - 1;
            candidateGroupList.add(new CandidateGroup(candidatesList.get(0).getNumber()));

            //loop all the other candidates
            for (PConditional currentCandidate : candidatesList) {

                //increment lastConditionalNumber is candidate is 1 above the last
                if (currentCandidate.getNumber() == lastConditionalNumber + 1)
                    lastConditionalNumber = currentCandidate.getNumber();

                    //else finish the group and start new group
                else {
                    candidateGroupList.get(candidateGroupList.size() - 1).setLastNumber(lastConditionalNumber);

                    candidateGroupList.add(new CandidateGroup(currentCandidate.getNumber()));

                    lastConditionalNumber = currentCandidate.getNumber();

                }

            }
            candidateGroupList.get(candidateGroupList.size() - 1).setLastNumber(lastConditionalNumber);


            for (int i = 0; i < candidateGroupList.size(); i++) {
                sb.append(candidateGroupList.get(i).toString());
                if (i != (candidateGroupList.size() - 1))
                    sb.append(",");
            }
        } else sb.append("EMPTY");

        return sb.toString();
    }

}
