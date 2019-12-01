package kb_creator.model.pairs;

import kb_creator.model.propositional_logic.KnowledgeBase;
import kb_creator.model.propositional_logic.PConditional;

import java.util.List;
import java.util.Map;

public abstract class AbstractPair {

    protected KnowledgeBase knowledgeBase;
    protected static Map<Integer, PConditional> nfcMap;

    //concrete methods
    public KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    public static void setNfc(Map<Integer, PConditional> nfc) {
        nfcMap = nfc;
    }


    //abstract methods

    public abstract List<PConditional> getCandidatesList();

    public abstract void clear();

    public abstract String toFileString();

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

}
