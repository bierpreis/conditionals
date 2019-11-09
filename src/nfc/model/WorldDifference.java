package nfc.model;

import kb_creator.model.propositional_logic.signature.AB;
import kb_creator.model.propositional_logic.signature.ABC;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.ArrayList;
import java.util.Arrays;
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
        List<WorldsPair> differenceList = new ArrayList<>();

        //if the sets are different sizes, they cant be equivalent
        if (worldList1.getSize() != worldList2.getSize())
            return false;

        //iterate trough list and collect differences in difference list
        for (int i = 0; i < worldList1.getSize(); i++) {
            if (!worldList1.getWorldsList().get(i).equals(worldList2.getWorldsList().get(i)))
                differenceList.add(new WorldsPair(worldList1.getWorldsList().get(i), worldList2.getWorldsList().get(i)));
        }

        for (WorldsPair worldsPair : differenceList) {
            if (!worldsPair.isEquivalent())
                return false;
        }
        return true;
    }

    public static void setSignature(AbstractSignature requestedSignature) {
        signature = requestedSignature;
    }

    private class WorldsPair {
        //todo: eq group in signature?
        private List<Integer> equivalenceGroup = Arrays.asList(2, 1);

        private final List<Integer> equivalenceGroup1 = Arrays.asList(6, 5, 3);
        private final List<Integer> equivalenceGroup2 = Arrays.asList(4, 2, 1);


        private final int firstInt;
        private final int secondInt;

        public WorldsPair(int firstInt, int secondInt) {
            this.firstInt = firstInt;
            this.secondInt = secondInt;
        }

        public boolean isEquivalent() {
            if (signature instanceof AB)
                return (equivalenceGroup.contains(firstInt) && equivalenceGroup.contains(secondInt));


            if (signature instanceof ABC) {
                if (equivalenceGroup1.contains(firstInt) && equivalenceGroup1.contains(secondInt))
                    return true;
                else return (equivalenceGroup2.contains(firstInt) && equivalenceGroup2.contains(secondInt));
            }

            throw new RuntimeException("Wrong signature: " + signature);

        }


    }


}
