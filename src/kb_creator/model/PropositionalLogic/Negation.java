package kb_creator.model.PropositionalLogic;

public class Negation extends AbstractFormula {
    private AbstractFormula formula;

    public Negation(AbstractFormula formula) {
        this.formula = formula;
    }

    public String toString() {
        return "!" + formula.toString();
    }

    @Override
    public boolean evaluate(Interpretation interpretation) {
        return !formula.evaluate(interpretation);
    }
}
