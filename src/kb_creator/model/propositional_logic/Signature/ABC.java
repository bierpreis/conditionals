package kb_creator.model.propositional_logic.Signature;


import kb_creator.model.propositional_logic.Worlds.ABCWorld;

public class ABC extends AbstractSignature {


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
