package kb_creator.model.Conditionals.KnowledgeBase;

import kb_creator.model.Conditionals.NewConditional;
import kb_creator.model.Signature.AbstractSignature;

import java.util.List;
import java.util.Map;

public abstract class AbstractKnowledgeBase {
    protected static Map<Integer, NewConditional> nfcMap;
    protected AbstractSignature signature;
    protected int kbNumber;

    public abstract String toFileString();

    public abstract String toShortFileString();

    public abstract void add(NewConditional conditional);

    public abstract int getSize();

    public static void setNfcMap(Map<Integer, NewConditional> nfcMapToAdd) {
        nfcMap = nfcMapToAdd;
    }

    public abstract List<NewConditional> getConditionalList();

    public int getKbNumber() {
        return kbNumber;
    }

    public abstract boolean isConsistent(NewConditional conditional);

}
