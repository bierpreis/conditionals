package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.worlds.ABCWorld;
import kb_creator.model.propositional_logic.worlds.ABWorld;
import kb_creator.model.propositional_logic.worlds.AbstractWorld;

public class Atom extends AbstractFormula {
    private Var variable;

    public Atom(Var variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        return variable.toString();
    }


    public boolean evaluate(AbstractWorld world) {

        if (world instanceof ABCWorld) {
            ABCWorld abcWorld = (ABCWorld) world;
            if (variable.equals(Var.a))
                return abcWorld.isA();

            if (variable.equals(Var.b))
                return abcWorld.isB();

            if (variable.equals(Var.c))
                return abcWorld.isC();

            throw new RuntimeException("Invalid variable found: " + variable);
        } else if (world instanceof ABWorld) {
            ABWorld abWorld = (ABWorld) world;
            if (variable.equals(Var.a))
                return abWorld.isA();

            if (variable.equals(Var.b))
                return abWorld.isB();

            throw new RuntimeException("Invalid variable found: " + variable);
        }
        throw new RuntimeException("No possible World found!" + world);
    }
}
