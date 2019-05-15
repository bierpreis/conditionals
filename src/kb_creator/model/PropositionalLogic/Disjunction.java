package kb_creator.model.PropositionalLogic;

public class Disjunction extends AbstractFormula {
    private AbstractFormula firstFormula;
    private AbstractFormula secondFormula;


    public Disjunction(AbstractFormula firstFormula, AbstractFormula secondFormula) {
        this.firstFormula = firstFormula;
        this.secondFormula = secondFormula;
    }

    @Override
    public boolean evaluate(Interpretation interpretation) {
        return firstFormula.evaluate(interpretation) || secondFormula.evaluate(interpretation);

    }

    @Override //todo: should or be a simple comma??
    public String toString() {
        return "(" + firstFormula.toString() + "(or)" + secondFormula.toString() + ")";
    }

    //todo: test this
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Disjunction))
            return false;
        Disjunction otherDisjunction = (Disjunction) o;

        if (otherDisjunction.getFirstFormula().equals(firstFormula) && otherDisjunction.getSecondFormula().equals(secondFormula))
            return true;
        if (otherDisjunction.getFirstFormula().equals(secondFormula) && otherDisjunction.getSecondFormula().equals(firstFormula))
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
