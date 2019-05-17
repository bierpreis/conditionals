package kb_creator.model.PropositionalLogic;

public abstract class AbstractFormula {

    abstract public boolean evaluate(PossibleWorldABC interpretation);

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

    public boolean isAtom() {
        if (this instanceof Atom) return true;
        if (this instanceof Negation)
            return ((Negation) this).isNegatedAtom();
        return false;
    }

}
