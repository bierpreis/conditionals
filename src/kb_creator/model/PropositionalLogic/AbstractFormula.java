package kb_creator.model.PropositionalLogic;

public abstract class AbstractFormula {

    abstract boolean evaluate(Interpretation interpretation);

    public AbstractFormula and(AbstractFormula otherFormula) {
        return new Conjunction(this, otherFormula);
    }

    public AbstractFormula or(AbstractFormula otherFormula) {
        return new Disjunction(this, otherFormula);
    }

    public AbstractFormula neg() {
        return new Negation(this);

    }

}
