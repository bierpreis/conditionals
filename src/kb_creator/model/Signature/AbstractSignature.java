package kb_creator.model.Signature;


import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSignature {
    protected List<PossibleWorld> possibleWorlds;

    public AbstractSignature(){
        possibleWorlds = new LinkedList<>();
    }

    //todo: return this with commas and lowerCase
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public List<PossibleWorld> getPossibleWorlds() {
        return possibleWorlds;
    }


}
