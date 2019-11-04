package nfc.model;

import kb_creator.model.propositional_logic.signature.AB;
import kb_creator.model.propositional_logic.signature.ABC;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorldDifference {
    private List<Integer> equivalenceGroup = Arrays.asList(2, 1);

    private final List<Integer> equivalenceGroup1 = Arrays.asList(6, 5, 3);
    private final List<Integer> equivalenceGroup2 = Arrays.asList(4, 2, 1);

    private static AbstractSignature signature;
    private final WorldSet worldSet1;
    private final WorldSet worldSet2;

    private final List<WorldsPair> differenceList;

    private boolean areEquivalent;

    public WorldDifference(WorldSet worldSet1, WorldSet worldSet2) {
        this.worldSet1 = worldSet1;
        this.worldSet2 = worldSet2;

        differenceList = new ArrayList<>();
        areEquivalent = false;

        calculateDifference();
    }

    private void calculateDifference() {

        //if the sets are different sizes, they cant be equal
        if (worldSet1.getSize() != worldSet2.getSize())
            return;

        //iterate trough list and collect differences in difference list
        for (int i = 0; i < worldSet1.getSize(); i++) {
            if (!worldSet1.getWorldsList().get(i).equals(worldSet2.getWorldsList().get(i)))
                differenceList.add(new WorldsPair(worldSet1.getWorldsList().get(i), worldSet2.getWorldsList().get(i)));
        }

        List<WorldsPair> cleanedDifferenceList = new ArrayList<>();
        for (WorldsPair worldsPair : differenceList) {
            if (!worldsPair.isEquivalent())
                cleanedDifferenceList.add(worldsPair);
        }

        if (cleanedDifferenceList.size() == 0)
            areEquivalent = true;


    }

    public boolean areEquivalent() {
        return areEquivalent;
    }

    public static void setSignature(AbstractSignature requestedSignature) {
        signature = requestedSignature;
    }

    private class WorldsPair {
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
