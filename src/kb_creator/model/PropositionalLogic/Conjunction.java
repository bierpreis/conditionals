package kb_creator.model.PropositionalLogic;

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

    public String toString() {
        String stringToReturn = "";
        return "{" + firstFormula.toString() + "(and)" + secondFormula + "}";
    }
}
