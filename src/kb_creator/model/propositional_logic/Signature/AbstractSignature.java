package kb_creator.model.propositional_logic.Signature;

import kb_creator.model.propositional_logic.Worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSignature {
    protected List<AbstractWorld> possibleWorlds;

    public AbstractSignature() {
        possibleWorlds = new ArrayList<>();
    }


    @Override
    abstract public String toString();

    public List<AbstractWorld> getPossibleWorlds() {
        return possibleWorlds;
    }


}
