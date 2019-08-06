package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.worlds.AbstractWorld;

public class Negation extends AbstractFormula {
    private AbstractFormula formula;

    public Negation(AbstractFormula formula) {
        this.formula = formula;
    }

    @Override
    public String toString() {
        if (formula instanceof Atom)
            return "!" + formula.toString();
        else return ("!(" + formula + ")");
    }

    @Override
    public boolean evaluate(AbstractWorld world) {
        return !formula.evaluate(world);
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

    private AbstractFormula getFormula() {
        return formula;
    }


}
