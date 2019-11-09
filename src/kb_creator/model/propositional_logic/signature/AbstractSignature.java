package kb_creator.model.propositional_logic.signature;

import kb_creator.model.propositional_logic.worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSignature {
    protected List<AbstractWorld> possibleWorlds;

    protected List<List<Integer>> equivalenceGroups = new ArrayList<>();

    @Override
    abstract public String toString();

    public List<AbstractWorld> getPossibleWorlds() {
        return possibleWorlds;
    }

    public List<List<Integer>> getEqGroups() {
        return equivalenceGroups;
    }


}
