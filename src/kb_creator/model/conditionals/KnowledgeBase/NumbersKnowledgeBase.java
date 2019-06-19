package kb_creator.model.conditionals.KnowledgeBase;

import kb_creator.model.conditionals.NewConditional;
import kb_creator.model.propositional_logic.AbstractFormula;
import kb_creator.model.propositional_logic.Tautology;
import kb_creator.model.propositional_logic.Worlds.AbstractWorld;
import kb_creator.model.propositional_logic.Signature.AbstractSignature;

import java.util.ArrayList;
import java.util.List;

public class NumbersKnowledgeBase extends AbstractKnowledgeBase {
    private List<Integer> conditionalNumbersList;

    public NumbersKnowledgeBase(AbstractSignature signature, int kbNumber) {
        conditionalNumbersList = new ArrayList<>();
        this.signature = signature;
        this.kbNumber = kbNumber;
    }

    @Override
    public boolean isConsistent(NewConditional conditionalToTest) {
        //this test is written in goldszmit/pearl 1996 p 64 (tolerance)
        //siehe auch infofc s 4 dazu. auch s 9 dort.

        List<NewConditional> conditionalList = new ArrayList<>();
        for (Integer conditionalNumber : conditionalNumbersList) {
            conditionalList.add(nfcMap.get(conditionalNumber));
        }


        AbstractFormula concistecyOfKB = new Tautology();

        for (NewConditional conditionalFromList : conditionalList) {
            concistecyOfKB = concistecyOfKB.and(conditionalFromList.getAntecend().neg().or(conditionalFromList.getConsequence()));
        }


        for (AbstractWorld world : signature.getPossibleWorlds()) {
            if (conditionalToTest.getAntecend().evaluate(world) && conditionalToTest.getConsequence().evaluate(world) && concistecyOfKB.evaluate(world)) {
                return true;

            }
        }
        return false;
    }

    @Override
    public String toShortFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("signature");
        sb.append(signature.toString());
        sb.append("\n\n");
        sb.append("conditionals: \n\n");
        for (Integer conditional : conditionalNumbersList) {
            sb.append(conditional);
            sb.append(", ");
        }
        return sb.toString().replace(", \n$", "");
    }

    @Override
    public void add(AbstractKnowledgeBase knowledgeBaseToAdd) {
        for (NewConditional conditionalToAdd : knowledgeBaseToAdd.getConditionalList()) {
            conditionalNumbersList.add(conditionalToAdd.getNumber());
        }
    }

    @Override
    public int getSize() {
        return conditionalNumbersList.size();
    }

    @Override
    public void add(NewConditional conditionalToAdd) {
        conditionalNumbersList.add(conditionalToAdd.getNumber());
    }

    @Override
    public String toFileString() {

        StringBuilder sb = new StringBuilder();
        sb.append("signature\n");
        sb.append(signature.toString().toLowerCase());
        sb.append("\n");
        sb.append("conditionals\n\n");
        sb.append(this.kbNumber);
        sb.append("{\n");

        List<NewConditional> conditionalList = getConditionalList();
        for (int i = 0; i < conditionalList.size(); i++) {
            sb.append(conditionalList.get(i));
            if (i != conditionalList.size() - 1)
                sb.append(",\n");

        }
        sb.append("\n}");
        return sb.toString();

    }

    @Override
    public String toString() {
        return getConditionalList().toString();
    }

    @Override
    public List<NewConditional> getConditionalList() {
        List<NewConditional> conditionalList = new ArrayList<>();
        for (Integer conditionalNumber : conditionalNumbersList) {
            conditionalList.add(nfcMap.get(conditionalNumber));
        }
        return conditionalList;
    }
}
