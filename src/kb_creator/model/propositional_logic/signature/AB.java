package kb_creator.model.propositional_logic.signature;


import kb_creator.model.propositional_logic.worlds.ABWorld;

import java.util.ArrayList;

public class AB extends AbstractSignature {

    //todo: this is opposite order of abc world. fix.
    public AB() {
        possibleWorlds = new ArrayList<>(4);
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
