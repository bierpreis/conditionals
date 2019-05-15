package kb_creator.model.PropositionalLogic;

public class Negation extends AbstractFormula {
    private AbstractFormula formula;

    public Negation(AbstractFormula formula) {
        this.formula = formula;
    }

    //todo: why is this never visible?
    @Override
    public String toString() {
        return "!" + formula.toString();
    }

    @Override
    public boolean evaluate(Interpretation interpretation) {
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
