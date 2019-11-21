package kb_creator.model.propositional_logic.worlds;

import kb_creator.model.propositional_logic.Var;

public abstract class AbstractWorld {

    @Override
    public abstract String toString();

    public abstract boolean get(Var var);
}
