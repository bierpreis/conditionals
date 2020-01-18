package kb_creator.model.logic;

import kb_creator.model.logic.world.AbstractWorld;

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

        return world.get(variable);
    }
}
