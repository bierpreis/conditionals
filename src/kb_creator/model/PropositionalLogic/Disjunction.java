package kb_creator.model.PropositionalLogic;

import kb_creator.model.Signature.ABC;
import kb_creator.model.Signature.PossibleWorld;

public class Disjunction extends AbstractFormula {
    private AbstractFormula firstFormula;
    private AbstractFormula secondFormula;


    public Disjunction(AbstractFormula firstFormula, AbstractFormula secondFormula) {
        this.firstFormula = firstFormula;
        this.secondFormula = secondFormula;
    }

    @Override
    public boolean evaluate(PossibleWorld world) {
        return firstFormula.evaluate(world) || secondFormula.evaluate(world);

    }


    public String toString() {
        return "(" + firstFormula.toString() + "," + secondFormula.toString() + ")";
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Disjunction))
            return false;
        Disjunction otherDisjunction = (Disjunction) o;

        if (otherDisjunction.getFirstFormula().equals(firstFormula) && otherDisjunction.getSecondFormula().equals(secondFormula))
            return true;
        if (otherDisjunction.getFirstFormula().equals(secondFormula) && otherDisjunction.getSecondFormula().equals(firstFormula))
            return true;
        return false;
    }

    public AbstractFormula getFirstFormula() {
        return firstFormula;
    }

    public AbstractFormula getSecondFormula() {
        return secondFormula;
    }

}
