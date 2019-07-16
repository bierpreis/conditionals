package kb_creator.model.conditionals.pairs;

import kb_creator.model.conditionals.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.conditionals.NewConditional;

import java.util.List;
import java.util.Map;

//the reason for this abstract class is to test different candidate pair implementations for performance reasons
public abstract class AbstractPair {

    AbstractPair(){

    }
    AbstractPair(AbstractKnowledgeBase kb,  List<NewConditional> list){

    }
    protected AbstractKnowledgeBase knowledgeBase;
    protected static Map<Integer, NewConditional> nfcMap;

    public void deleteKB() {
        knowledgeBase = null;
    }

    public int getNumber() {
        return knowledgeBase.getKbNumber();
    }

    public static void setNfc(Map<Integer, NewConditional> nfc) {
        nfcMap = nfc;
    }

    public AbstractKnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    public abstract List<NewConditional> getCandidatesList();

    public abstract String toString();

    public abstract void deleteCandidates();

    public abstract String toFileString();
}
