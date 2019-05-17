package kb_creator.model.Signature;



import java.util.List;

public abstract class AbstractSignature {
    List<PossibleWorld> possibleWorlds;

    @Override
    public String toString() {
        return this.getClass().getSimpleName();

        //todo: possible worlds = ...
    }

    public List<PossibleWorld> getPossibleWorlds() {
        return possibleWorlds;
    }

}
