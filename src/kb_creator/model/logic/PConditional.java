package kb_creator.model.logic;

import kb_creator.model.logic.signature.worlds.AbstractWorld;
import nfc_creator.model.ConditionalTranslator;

import java.util.ArrayList;
import java.util.List;

//todo: test in debugger if new translating works like it should.
public class PConditional {
    private final int number;

    //todo: describe
    private final AbstractFormula normalAntecedent;
    private final AbstractFormula normalConsequence;

    private final AbstractFormula shortAntecedent;
    private final AbstractFormula shortConsequence;

    //todo: experiment with random object of some size
    
    private final AbstractFormula toleranceFormula;

    private PConditional counterConditional;

    //empty list as default for all conditionals who will not have any equivalent conditionals
    private List<PConditional> eqConditionalsList = new ArrayList<>(0);

    public PConditional(AbstractFormula consequence, AbstractFormula shortConsequence, AbstractFormula antecedent, AbstractFormula shortAntecedent, int number) {
        this.normalConsequence = consequence;
        this.shortConsequence = shortConsequence;

        this.normalAntecedent = antecedent;
        this.shortAntecedent = shortAntecedent;

        //todo: test this twisted
        this.toleranceFormula = shortAntecedent.neg().or(shortConsequence);

        this.number = number;

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



    //getters

    public PConditional getCounterConditional() {
        return counterConditional;
    }


    public AbstractFormula getAntecedent() {
        return shortAntecedent;
    }

    public AbstractFormula getConsequence() {
        return shortConsequence;
    }

    public int getNumber() {
        return number;
    }

    public List<PConditional> getEqConditionalsList() {
        return eqConditionalsList;
    }

}