package kb_creator.model.Conditionals;

import kb_creator.model.PropositionalLogic.AbstractFormula;
import kb_creator.model.PropositionalLogic.Tautology;

import java.util.LinkedList;
import java.util.List;

public class NewKnowledgeBase {
    private List<NewConditional> conditionalList;
    private String name = "";

    public NewKnowledgeBase() {
        conditionalList = new LinkedList<>();
    }

    public boolean isConsistent(NewConditional conditional) {
        //todo this test is written in goldszmit/pearl 1996 p 64 (tolerance)
        //siehe auch infofc s 4 dazu. auch s 9 dort.


        AbstractFormula concistecyOfKB = new Tautology();

        for (NewConditional conditionalFromList : conditionalList) {
            concistecyOfKB = concistecyOfKB.and(conditionalFromList.getAntecend().neg().or(conditionalFromList.getConsequence()));
        }

        //here sth like:

        //for(interpretation interpretation){
        //if conditional.antecend.ev(interpretation) && conditional.consequence.evaluate(interpretation) && consictencyofKB.ev(interpretation)
        return true;
        //}else return false;
    }

    public void add(NewConditional conditional) {
        conditionalList.add(conditional);
    }

    public void add(NewKnowledgeBase knowledgeBaseToAdd) {
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
        //todo: add signature to string + "\n"

        stringToReturn = stringToReturn + "\n";
        stringToReturn = stringToReturn + "conditionals\n";
        stringToReturn = stringToReturn + this.name + "{";

        for (NewConditional conditional : conditionalList)
            stringToReturn = stringToReturn + conditional.toString() + ",\n";

        stringToReturn = stringToReturn + "}";
        return stringToReturn;
    }

}
