package nfc.model;

import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.ArrayList;
import java.util.List;

public class WorldDifference {
    private static AbstractSignature signature;
    private final WorldList worldList1;
    private final WorldList worldList2;


    public WorldDifference(WorldList worldList1, WorldList worldList2) {
        this.worldList1 = worldList1;
        this.worldList2 = worldList2;
    }

    public boolean areEquivalent() {

        //if the sets are different sizes, they cant be equivalent
        if (worldList1.getSize() != worldList2.getSize())
            return false;

        //iterate trough lists and check if element pairs are equivalent
        for (int i = 0; i < worldList1.getSize(); i++) {

            //get a pair of worlds
            if (!worldList1.getWorldsList().get(i).equals(worldList2.getWorldsList().get(i))) {
                boolean equivalent = false;

                //check if the pair is equivalent
                for (List<Integer> equivalenceGroup : signature.getEqGroups()) {
                    if ((equivalenceGroup.contains(worldList1.getWorldsList().get(i)) && equivalenceGroup.contains(worldList2.getWorldsList().get(i))))
                        equivalent = true;
                }

                //return false when the first pair is not equivalent
                if (!equivalent)
                    return false;
            }
        }

        //return true if all pairs are equivalent
        return true;
    }

    public static void setSignature(AbstractSignature requestedSignature) {
        signature = requestedSignature;
    }
}
