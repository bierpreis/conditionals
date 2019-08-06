package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.worlds.AbstractWorld;

import java.util.List;

public abstract class AbstractFormula {

    abstract public boolean evaluate(AbstractWorld world);

    //todo: override in conjunction and disjunction!
    //this will be overwritten in some subclasses
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
