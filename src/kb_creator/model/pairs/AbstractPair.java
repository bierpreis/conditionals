package kb_creator.model.pairs;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.propositional_logic.PConditional;

import java.util.List;
import java.util.Map;

//the reason for this abstract class is to test different candidate pair implementations for performance reasons
public abstract class AbstractPair {

    protected AbstractKnowledgeBase knowledgeBase;
    protected static Map<Integer, PConditional> nfcMap;

    //concrete methods
    public AbstractKnowledgeBase getKnowledgeBase() {
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
