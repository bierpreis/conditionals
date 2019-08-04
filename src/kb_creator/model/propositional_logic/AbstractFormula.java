package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.worlds.AbstractWorld;

public abstract class AbstractFormula {

    abstract public boolean evaluate(AbstractWorld world);

    public AbstractFormula and(AbstractFormula otherFormula) {
        return new Conjunction(this, otherFormula);
    }


    public AbstractFormula or(AbstractFormula otherFormula) {
        return new Disjunction(this, otherFormula);
    }

    abstract public AbstractFormula neg();

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
