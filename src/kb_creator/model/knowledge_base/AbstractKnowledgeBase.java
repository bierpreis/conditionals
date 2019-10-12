package kb_creator.model.knowledge_base;

import kb_creator.model.propositional_logic.NewConditional;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.List;
import java.util.Map;

//this class is made to use diffrent knowledge base implementations
//but the actual impelementation seems not to be important
public abstract class AbstractKnowledgeBase {

    protected static Map<Integer, NewConditional> nfcMap;
    protected static AbstractSignature signature;
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

    public AbstractSignature getSignature() {
        return signature;
    }

    public static void setSignature(AbstractSignature signatureToSet) {
        signature = signatureToSet;
    }

    public void setKbNumber(int kbNumber) {

        //exception to make sure numbers are set only once
        if (this.kbNumber != 0)
            throw new RuntimeException("Cant set kb number two times!");

        this.kbNumber = kbNumber;
    }
}
