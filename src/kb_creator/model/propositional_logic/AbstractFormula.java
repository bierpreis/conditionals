package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.signature.AbstractSignature;
import kb_creator.model.propositional_logic.worlds.AbstractWorld;


public abstract class AbstractFormula {

    //only purpose if signature is equals, which is only used for test when translating sets of worlds into formulas
    protected static AbstractSignature signature;

    abstract public boolean evaluate(AbstractWorld world);

    //gets overwritten in conjunction
    public AbstractFormula and(AbstractFormula otherFormula) {
        return new Conjunction(this, otherFormula);
    }

    //gets overwritten in disjunction
    public AbstractFormula or(AbstractFormula otherFormula) {
        return new Disjunction(this, otherFormula);
    }

    //todo: really needs to be abstract? is this overwritten by anything then negation?
    abstract public AbstractFormula neg();

    //eq to make the name diffrent than equals
    public AbstractFormula eq(AbstractFormula otherFormula) {
        return new Equality(this, otherFormula);
    }

    @Override
    public abstract String toString();

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof AbstractFormula))
            return false;

        for (AbstractWorld world : signature.getPossibleWorlds()) {
            if (!this.evaluate(world) == ((AbstractFormula) o).evaluate(world))
                return false;
        }
        return true;
    }

    public static void setSignature(AbstractSignature requestedSignature) {
        signature = requestedSignature;
    }

}
