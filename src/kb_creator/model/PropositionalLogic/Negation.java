package kb_creator.model.PropositionalLogic;

import kb_creator.model.Signature.ABC;

public class Negation extends AbstractFormula {
    private AbstractFormula formula;

    public Negation(AbstractFormula formula) {
        this.formula = formula;
    }


    @Override
    public String toString() {
        return "!" + formula.toString();
    }

    @Override
    public boolean evaluate(ABC interpretation) {
        return !formula.evaluate(interpretation);
    }

    @Override
    public AbstractFormula neg() {
        return formula;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Negation))
            return false;
        Negation otherNegation = (Negation) o;
        return otherNegation.getFormula().equals(formula);
    }

    public AbstractFormula getFormula() {
        return formula;
    }

    public boolean isNegatedAtom() {
        return formula instanceof Atom;
    }

}
