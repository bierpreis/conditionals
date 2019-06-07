package kb_creator.model.Conditionals.Pairs;

import kb_creator.model.Conditionals.KnowledgeBase.AbstractKnowledgeBase;
import kb_creator.model.Conditionals.NewConditional;

import java.util.List;
import java.util.Map;

//the reason for this abstract class is only to test diffrent candidate pair implementations for performance reasons
public abstract class AbstractPair {
    protected AbstractKnowledgeBase knowledgeBase;

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

    public abstract String toShortString();

    public abstract String toFileString();
}
