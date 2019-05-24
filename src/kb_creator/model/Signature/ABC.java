package kb_creator.model.Signature;


public class ABC extends AbstractSignature {


    public ABC() {
        possibleWorlds.add(new PossibleWorld(false, false, false));
        possibleWorlds.add(new PossibleWorld(false, false, true));
        possibleWorlds.add(new PossibleWorld(false, true, false));
        possibleWorlds.add(new PossibleWorld(false, true, true));
        possibleWorlds.add(new PossibleWorld(true, false, false));
        possibleWorlds.add(new PossibleWorld(true, false, true));
        possibleWorlds.add(new PossibleWorld(true, true, false));
        possibleWorlds.add(new PossibleWorld(true, true, true));

    }


    @Override
    public String toString() {
        return "a,b,c";
    }

}
