package kb_creator.model.Conditionals;

import kb_creator.model.PropositionalLogic.AbstractFormula;
import kb_creator.model.PropositionalLogic.Tautology;
import kb_creator.model.Signature.AbstractSignature;
import kb_creator.model.Signature.PossibleWorld;

import java.util.LinkedList;
import java.util.List;

public class KnowledgeBase {
    private AbstractSignature signature;
    private List<NewConditional> conditionalList;
    private String name = "";

    public KnowledgeBase(AbstractSignature signature) {
        conditionalList = new LinkedList<>();
        this.signature = signature;
    }

    public boolean isConsistent(NewConditional conditional) {
        //this test is written in goldszmit/pearl 1996 p 64 (tolerance)
        //siehe auch infofc s 4 dazu. auch s 9 dort.


        AbstractFormula concistecyOfKB = new Tautology();

        for (NewConditional conditionalFromList : conditionalList) {
            concistecyOfKB = concistecyOfKB.and(conditionalFromList.getAntecend().neg().or(conditionalFromList.getConsequence()));
        }

        //here sth like:

        for (PossibleWorld world : signature.getPossibleWorlds()) {
            if (conditional.getAntecend().evaluate(world) && conditional.getConsequence().evaluate(world) && concistecyOfKB.evaluate(world))
                return true;


        }
        return false;
    }

    public void add(NewConditional conditional) {
        conditionalList.add(conditional);
    }

    public void add(KnowledgeBase knowledgeBaseToAdd) {
        for (NewConditional conditional : knowledgeBaseToAdd.conditionalList) {
            conditionalList.add(conditional);
        }
    }

    public List<NewConditional> getConditionalList() {
        return conditionalList;
    }

    @Override
    public String toString() {
        return conditionalList.toString();
    }


    public String toFileString() {
        String stringToReturn = "signature\n";
        stringToReturn = stringToReturn + signature.toString().toLowerCase();

        stringToReturn = stringToReturn + "\n";
        stringToReturn = stringToReturn + "conditionals\n";
        stringToReturn = stringToReturn + this.name + "{";

        for (NewConditional conditional : conditionalList)
            stringToReturn = stringToReturn + conditional.toString() + ",\n";

        stringToReturn = stringToReturn + "}";
        return stringToReturn;
    }

}
