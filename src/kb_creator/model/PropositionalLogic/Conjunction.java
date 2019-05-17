package kb_creator.model.PropositionalLogic;

import kb_creator.model.Signature.ABC;

public class Conjunction extends AbstractFormula {
    private AbstractFormula firstFormula;
    private AbstractFormula secondFormula;

    public Conjunction(AbstractFormula firstFormula, AbstractFormula secondFormula) {
        this.firstFormula = firstFormula;
        this.secondFormula = secondFormula;
    }

    @Override
    public boolean evaluate(ABC interpretation) {
        return firstFormula.evaluate(interpretation) && secondFormula.evaluate(interpretation);
    }

    @Override
    public String toString() {
        if (firstFormula.isAtom() && secondFormula.isAtom())
            return firstFormula.toString() + secondFormula.toString();
        return "(" + firstFormula.toString() + "(and)" + secondFormula.toString() + ")";
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Conjunction))
            return false;
        Conjunction otherConjunction = (Conjunction) o;

        if (otherConjunction.getFirstFormula().equals(firstFormula) && otherConjunction.getSecondFormula().equals(secondFormula))
            return true;
        if (otherConjunction.getFirstFormula().equals(secondFormula) && otherConjunction.getSecondFormula().equals(firstFormula))
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
