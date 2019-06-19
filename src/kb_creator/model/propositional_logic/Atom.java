package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.Worlds.ABCWorld;
import kb_creator.model.propositional_logic.Worlds.ABWorld;
import kb_creator.model.propositional_logic.Worlds.AbstractWorld;

public class Atom extends AbstractFormula {
    private Variable variable;

    public Atom(Variable variable) {
        this.variable = variable;
    }

    public Variable get() {
        return variable;
    }

    @Override
    public String toString() {
        return variable.toString();
    }

    public boolean evaluate(AbstractWorld world) {

        if (world instanceof ABCWorld) {
            ABCWorld abcWorld = (ABCWorld) world;
            if (variable.equals(Variable.a))
                return abcWorld.isA();

            if (variable.equals(Variable.b))
                return abcWorld.isB();

            if (variable.equals(Variable.c))
                return abcWorld.isC();

        }

        if (world instanceof ABWorld) {
            ABWorld abWorld = (ABWorld) world;
            if (variable.equals(Variable.a))
                return abWorld.isA();

            if (variable.equals(Variable.b))
                return abWorld.isB();
        }
        throw new RuntimeException("No possible World found!" + world);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Atom))
            return false;
        Atom otherAtom = (Atom) o;
        return otherAtom.get().equals(variable);
    }
}
