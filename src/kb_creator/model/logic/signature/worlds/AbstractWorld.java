package kb_creator.model.logic.signature.worlds;

import kb_creator.model.logic.Var;

public abstract class AbstractWorld {

    @Override
    public abstract String toString();

    public abstract boolean get(Var var);
}
