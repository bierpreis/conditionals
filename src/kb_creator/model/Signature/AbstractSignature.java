package kb_creator.model.Signature;


import java.util.List;

public abstract class AbstractSignature {
    protected List<PossibleWorld> possibleWorlds;

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public List<PossibleWorld> getPossibleWorlds() {
        return possibleWorlds;
    }


}
