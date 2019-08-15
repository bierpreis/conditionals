package kb_creator.model.propositional_logic.signature;

import kb_creator.model.propositional_logic.worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSignature {
    protected List<AbstractWorld> possibleWorlds;

    @Override
    abstract public String toString();

    public List<AbstractWorld> getPossibleWorlds() {
        return possibleWorlds;
    }


}
