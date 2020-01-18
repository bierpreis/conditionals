package kb_creator.model.logic.signature;

import kb_creator.model.logic.world.AbstractWorld;

import java.util.List;

public abstract class AbstractSignature {
    protected List<AbstractWorld> possibleWorlds;

    @Override
    abstract public String toString();

    public List<AbstractWorld> getPossibleWorlds() {
        return possibleWorlds;
    }

}
