package kb_creator.model.logic.signature;


import kb_creator.model.logic.world.ABWorld;

import java.util.ArrayList;


public class AB extends AbstractSignature {

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
