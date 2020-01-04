package kb_creator.model.logic;

import kb_creator.model.logic.signature.AbstractSignature;
import kb_creator.model.logic.signature.worlds.AbstractWorld;
import nfc_creator.model.ConditionalTranslator;

import java.util.ArrayList;
import java.util.List;

public class PConditional {
    private final int number;

    private final AbstractFormula antecedent;
    private final AbstractFormula consequence;


    private PConditional counterConditional;

    private static AbstractSignature signature;

    //empty list as default for all conditionals who will not have any equivalent conditionals
    private List<PConditional> eqConditionalsList = new ArrayList<>(0);

    public PConditional(AbstractFormula antecedent, AbstractFormula consequence, int number) {
        this.antecedent = antecedent;

        this.consequence = consequence;

        this.number = number;

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

    public boolean tolerates(AbstractWorld world) {
        return (consequence.evaluate(world) || !antecedent.evaluate(world));
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

    public static void setSignature(AbstractSignature signatureToSet) {
        signature = signatureToSet;
    }


    //quelle zu isToleratedBy:
    //this test is written in goldszmit/pearl 1996 p 64 (tolerance)

    public boolean isToleratedBy(List<PConditional> conditionalList) {
        for (AbstractWorld world : signature.getPossibleWorlds()) {
            if (this.antecedent.evaluate(world) && this.consequence.evaluate(world)) {
                boolean toleratesAll = true;
                for (PConditional conditional : conditionalList) {
                    if (!conditional.tolerates(world)) {
                        toleratesAll = false;
                        break;
                    }
                }
                if (toleratesAll)
                    return true;
            }
        }
        return false;
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