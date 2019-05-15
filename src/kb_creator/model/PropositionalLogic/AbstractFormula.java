package kb_creator.model.PropositionalLogic;

public abstract class AbstractFormula {

    abstract boolean evaluate(Interpretation interpretation);

    //todo: why is this never used
    public AbstractFormula and(AbstractFormula otherFormula) {
        return new Conjunction(this, otherFormula);
    }


    public AbstractFormula or(AbstractFormula otherFormula) {
        return new Disjunction(this, otherFormula);
    }

    public AbstractFormula neg() {
        return new Negation(this);

    }

    public abstract String toString();

    @Override
    public abstract boolean equals(Object o);

}
