package kb_creator.model.propositional_logic.signature;


import kb_creator.model.propositional_logic.worlds.ABCWorld;

public class ABC extends AbstractSignature {

    //todo: this is not consistent to ab world.
    //first: no super here
    //second: twisted oder of worlds added
    public ABC() {
        possibleWorlds.add(new ABCWorld(false, false, false));
        possibleWorlds.add(new ABCWorld(false, false, true));
        possibleWorlds.add(new ABCWorld(false, true, false));
        possibleWorlds.add(new ABCWorld(false, true, true));
        possibleWorlds.add(new ABCWorld(true, false, false));
        possibleWorlds.add(new ABCWorld(true, false, true));
        possibleWorlds.add(new ABCWorld(true, true, false));
        possibleWorlds.add(new ABCWorld(true, true, true));

    }


    @Override
    public String toString() {
        return "a,b,c";
    }

}
