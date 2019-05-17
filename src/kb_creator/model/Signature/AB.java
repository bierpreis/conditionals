package kb_creator.model.Signature;


public class AB extends AbstractSignature {


    public AB() {
        possibleWorlds.add(new PossibleWorld(true, true));
        possibleWorlds.add(new PossibleWorld(true, false));
        possibleWorlds.add(new PossibleWorld(false, true));
        possibleWorlds.add(new PossibleWorld(false, false));
    }

}
