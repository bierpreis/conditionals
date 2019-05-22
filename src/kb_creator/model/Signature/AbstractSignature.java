package kb_creator.model.Signature;


import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSignature {
    protected List<PossibleWorld> possibleWorlds;

    public AbstractSignature() {
        possibleWorlds = new LinkedList<>();
    }


    @Override
    abstract public String toString();

    public List<PossibleWorld> getPossibleWorlds() {
        return possibleWorlds;
    }


}
