package kb_creator.model.pairs;

import kb_creator.model.creator.KnowledgeBase;
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

    public abstract String toString();

    public abstract String toFileString();

    public abstract void clear();
}
