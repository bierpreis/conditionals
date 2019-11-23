package kb_creator.model.propositional_logic.signature;


import kb_creator.model.propositional_logic.worlds.ABCWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ABC extends AbstractSignature {

    public ABC() {
        possibleWorlds = new ArrayList<>(8);
        possibleWorlds.add(new ABCWorld(true, true, true));
        possibleWorlds.add(new ABCWorld(true, true, false));
        possibleWorlds.add(new ABCWorld(true, false, true));
        possibleWorlds.add(new ABCWorld(true, false, false));
        possibleWorlds.add(new ABCWorld(false, true, true));
        possibleWorlds.add(new ABCWorld(false, true, false));
        possibleWorlds.add(new ABCWorld(false, false, true));
        possibleWorlds.add(new ABCWorld(false, false, false));


    }


    @Override
    public String toString() {
        return "a,b,c";
    }

}
