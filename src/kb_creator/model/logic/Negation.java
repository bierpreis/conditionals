package kb_creator.model.logic;

import kb_creator.model.logic.world.AbstractWorld;

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

}
