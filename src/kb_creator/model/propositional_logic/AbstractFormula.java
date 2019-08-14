package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.worlds.AbstractWorld;


public abstract class AbstractFormula {

    abstract public boolean evaluate(AbstractWorld world);

    //this will be overwritten in some subclasses
    public AbstractFormula and(AbstractFormula otherFormula) {
        return new Conjunction(this, otherFormula);
    }

    //this will be overwritten in some subclasses
    public AbstractFormula or(AbstractFormula otherFormula) {
        return new Disjunction(this, otherFormula);
    }

    abstract public AbstractFormula neg();

    @Override
    public abstract String toString();

    //todo: some real equals method.
    //sth like:
    //for world world: worlds
    //if(o.evaluate!=this.evaluate
    //return false
    //else return true
    @Override
    public abstract boolean equals(Object o);

}
