package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.worlds.ABCWorld;
import kb_creator.model.propositional_logic.worlds.ABWorld;
import kb_creator.model.propositional_logic.worlds.AbstractWorld;

public class Atom extends AbstractFormula {
    private Variable variable;

    public Atom(Variable variable) {
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }

    @Override
    public String toString() {
        return variable.toString();
    }


    //todo: rethink
    public boolean evaluate(AbstractWorld world) {

        if (world instanceof ABCWorld) {
            ABCWorld abcWorld = (ABCWorld) world;
            if (variable.equals(Variable.a))
                return abcWorld.isA();

            if (variable.equals(Variable.b))
                return abcWorld.isB();

            if (variable.equals(Variable.c))
                return abcWorld.isC();

            throw new RuntimeException("Invalid variable found: " + variable);
        }

        if (world instanceof ABWorld) {
            ABWorld abWorld = (ABWorld) world;
            if (variable.equals(Variable.a))
                return abWorld.isA();

            if (variable.equals(Variable.b))
                return abWorld.isB();

            throw new RuntimeException("Invalid variable found: " + variable);
        }
        throw new RuntimeException("No possible World found!" + world);
    }


    @Override
    public AbstractFormula neg() {
        return new Negation(this);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Atom))
            return false;
        Atom otherAtom = (Atom) o;
        return otherAtom.getVariable().equals(variable);
    }
}
