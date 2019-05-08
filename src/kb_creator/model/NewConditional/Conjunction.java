package kb_creator.model.NewConditional;

public class Conjunction extends AbstractFormula {
    private AbstractFormula firstFormula;
    private AbstractFormula secondFormula;

    public Conjunction(AbstractFormula firstFormula, AbstractFormula secondFormula) {
        this.firstFormula = firstFormula;
        this.secondFormula = secondFormula;
    }

    @Override
    public boolean evaluate() {
        return firstFormula.evaluate() && secondFormula.evaluate();
    }
}
