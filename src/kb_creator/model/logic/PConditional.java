package kb_creator.model.logic;

import kb_creator.model.logic.signature.worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.List;

public class PConditional {
    private final int number;

    private final AbstractFormula normalAntecedent;
    private final AbstractFormula normalConsequence;

    private AbstractFormula shortAntecedent;
    private AbstractFormula shortConsequence;


    private final AbstractFormula toleranceFormula;

    private PConditional counterConditional;

    //empty list as default for all conditionals who will not have any equivalent conditionals
    private List<PConditional> eqConditionalsList = new ArrayList<>(0);

    public PConditional(AbstractFormula consequence, AbstractFormula antecedent, int number) {
        this.normalConsequence = consequence;
        this.normalAntecedent = antecedent;
        this.number = number;

        this.toleranceFormula = antecedent.neg().or(consequence);

    }

    public String toString() {
        return "(" + normalConsequence + " | " + normalAntecedent + ")";
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

    public boolean tolerates(AbstractWorld world) {
        return toleranceFormula.evaluate(world);
    }


    //setters
    public void setCounterConditional(PConditional counterConditional) {
        this.counterConditional = counterConditional;
    }

    public void setEqList(List<PConditional> eqConditionalsList) {
        if (this.eqConditionalsList.size() != 0)
            throw new RuntimeException("Eq Conditionals set twice! This should not happen.");
        this.eqConditionalsList = eqConditionalsList;
    }

    public void setShortAntecedent(AbstractFormula shortAntecedent) {
        this.shortAntecedent = shortAntecedent;
    }

    public void setShortConsequence(AbstractFormula shortConsequence) {
        this.shortConsequence = shortConsequence;
    }

    //getters

    public PConditional getCounterConditional() {
        return counterConditional;
    }

    //todo: should return short ones!

    public AbstractFormula getAntecedent() {
        return normalAntecedent;
    }

    public AbstractFormula getConsequence() {
        return normalConsequence;
    }

    public int getNumber() {
        return number;
    }

    public List<PConditional> getEqConditionalsList() {
        return eqConditionalsList;
    }

}