package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.worlds.AbstractWorld;

public abstract class AbstractFormula {

    abstract public boolean evaluate(AbstractWorld world);

    //todo: make abstract?! because this seems to be wrong. check also the is consistent method in object knowledge base for this as example.
    //at least for conjunction this is wrong?! or not?
    public AbstractFormula and(AbstractFormula otherFormula) {
        return new Conjunction(this, otherFormula);
    }


    public AbstractFormula or(AbstractFormula otherFormula) {
        return new Disjunction(this, otherFormula);
    }

    abstract public AbstractFormula neg();

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object o);


}
