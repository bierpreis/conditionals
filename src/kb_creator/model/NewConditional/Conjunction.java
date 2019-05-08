package kb_creator.model.NewConditional;

public class Conjunction extends AbstractFormula {
    private AbstractFormula firstFormula;
    private AbstractFormula secondFormula;

    public Conjunction(AbstractFormula firstFormula, AbstractFormula secondFormula) {
        this.firstFormula = firstFormula;
        this.secondFormula = secondFormula;
    }

    @Override
    public boolean evaluate(Interpretation interpretation) {
        return firstFormula.evaluate(interpretation) && secondFormula.evaluate(interpretation);
    }
}
