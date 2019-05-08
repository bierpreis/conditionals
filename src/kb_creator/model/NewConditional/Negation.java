package kb_creator.model.NewConditional;

public class Negation extends AbstractFormula {
    private AbstractFormula formula;

    public Negation(AbstractFormula formula) {
        this.formula = formula;
    }

    public String toString() {
        return "!" + formula.toString();
    }
}
