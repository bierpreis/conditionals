package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.List;

public class PConditional {
    private final int number;
    private final AbstractFormula antecedent;
    private final AbstractFormula consequence;
    private final AbstractFormula toleranceFormula;


    private PConditional counterConditional;
    
    //empty list as default for all conditionals who will not have any equivalent conditionals
    private List<PConditional> eqConditionalsList = new ArrayList<>(0);

    //todo: check how often this is instantiated
    public PConditional(AbstractFormula consequence, AbstractFormula antecedent, int number) {
        this.consequence = consequence;
        this.antecedent = antecedent;
        this.number = number;

        this.toleranceFormula = antecedent.neg().or(consequence);
    }

    public String toString() {
        return "(" + consequence + " | " + antecedent + ")";
    }



    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PConditional))
            return false;
        PConditional otherConditional = (PConditional) o;

        //uncomment for testing purposes
        //if (this.number == 0 || otherConditional.getNumber() == 0)
        //    throw new RuntimeException("Equals failed because numbers were wrong");

        //return this.antecedent.equals(otherConditional.getAntecedent()) && this.consequence.equals(otherConditional.getConsequence());

        return this.number == otherConditional.getNumber();

    }

    public void setCounterConditional(PConditional counterConditional) {
        this.counterConditional = counterConditional;
    }

    public void setEqList(List<PConditional> eqConditionalsList) {
        if (this.eqConditionalsList.size() != 0)
            throw new RuntimeException("Eq Conditionals set twice! This should not happen.");
        this.eqConditionalsList = eqConditionalsList;
    }



    public boolean tolerates(AbstractWorld world) {
        return toleranceFormula.evaluate(world);
    }

    //getters

    public PConditional getCounterConditional() {
        return counterConditional;
    }

    public AbstractFormula getAntecedent() {
        return antecedent;
    }

    public AbstractFormula getConsequence() {
        return consequence;
    }

    public int getNumber() {
        return number;
    }

    public List<PConditional> getEqConditionalsList() {
        return eqConditionalsList;
    }

}