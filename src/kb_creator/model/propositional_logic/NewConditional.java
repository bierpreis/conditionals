package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.List;

//todo: make stuff final
public class NewConditional {
    private final int number;
    private final AbstractFormula antecedent;
    private final AbstractFormula consequence;
    private final AbstractFormula toleranceFormula;


    private NewConditional counterConditional;
    //empty list as default for all conditionals who will not have any equivalent conditionals
    private List<NewConditional> eqConditionalsList = new ArrayList<>(0);


    public NewConditional(AbstractFormula consequence, AbstractFormula antecedent, int number) {
        this.consequence = consequence;
        this.antecedent = antecedent;
        this.number = number;

        this.toleranceFormula = antecedent.neg().or(consequence);
    }

    public String toString() {
        return "(" + consequence + " | " + antecedent + ")";
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NewConditional))
            return false;
        NewConditional otherConditional = (NewConditional) o;

        //uncomment for testing purposes
        //if (this.number == 0 || otherConditional.getNumber() == 0)
        //    throw new RuntimeException("Equals failed because numbers were wrong");

        //return this.antecedent.equals(otherConditional.getAntecedent()) && this.consequence.equals(otherConditional.getConsequence());

        return this.number == otherConditional.getNumber();

    }

    public NewConditional getCounterConditional() {
        return counterConditional;
    }

    public AbstractFormula getAntecedent() {
        return antecedent;
    }

    public AbstractFormula getConsequence() {
        return consequence;
    }


    public void setCounterConditional(NewConditional counterConditional) {
        this.counterConditional = counterConditional;
    }

    public void setEqList(List<NewConditional> eqConditionalsList) {
        if (this.eqConditionalsList.size() != 0)
            throw new RuntimeException("Eq Conditionals set twice! This should not happen.");
        this.eqConditionalsList = eqConditionalsList;
    }

    public List<NewConditional> getEqConditionalsList() {
        return eqConditionalsList;
    }

    public boolean tolerates(AbstractWorld world) {
        return toleranceFormula.evaluate(world);
    }

}