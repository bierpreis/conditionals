package kb_creator.model.propositional_logic.signature;


import kb_creator.model.propositional_logic.worlds.ABWorld;

public class AB extends AbstractSignature {


    public AB() {
        super();
        possibleWorlds.add(new ABWorld(true, true));
        possibleWorlds.add(new ABWorld(true, false));
        possibleWorlds.add(new ABWorld(false, true));
        possibleWorlds.add(new ABWorld(false, false));
    }

    @Override
    public String toString() {
        return "a,b";
    }

}
