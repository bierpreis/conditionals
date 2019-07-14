package kb_creator.model.conditionals.knowledge_base;

import kb_creator.model.conditionals.NewConditional;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.List;
import java.util.Map;

//this class is made to use diffrent knowledge base implementations
//but the actual impelementation is seems not to be important
public abstract class AbstractKnowledgeBase {
    protected static Map<Integer, NewConditional> nfcMap;
    protected AbstractSignature signature;
    protected int kbNumber;

    public abstract String toFileString();

    public abstract String toShortFileString();

    public abstract void add(NewConditional conditional);

    public abstract int getSize();

    public abstract List<NewConditional> getConditionalList();

    public abstract boolean isConsistent(NewConditional conditional);

    public abstract void add(AbstractKnowledgeBase knowledgeBaseToAdd);


    public static void setNfcMap(Map<Integer, NewConditional> nfcMapToAdd) {
        nfcMap = nfcMapToAdd;
    }

    public int getKbNumber() {
        return kbNumber;
    }
}
