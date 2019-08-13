package nfc.model;

import kb_creator.model.propositional_logic.signature.AB;
import kb_creator.model.propositional_logic.signature.ABC;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WorldDifference {
    private List<Integer> equivalenceGroup = Arrays.asList(2, 1);

    private final List<Integer> equivalenceGroup1 = Arrays.asList(6, 5, 3);
    private final List<Integer> equivalenceGroup2 = Arrays.asList(4, 2, 1);

    private static AbstractSignature signature;
    private final World world1;
    private final World world2;

    private final List<WorldsPair> differenceList;

    private boolean areEqual;

    public WorldDifference(World world1, World world2) {
        this.world1 = world1;
        this.world2 = world2;

        differenceList = new LinkedList<>();
        areEqual = false;

        calculateDifference();
    }

    private void calculateDifference() {
        if (world1.getSize() != world2.getSize())
            return;
        for (int i = 0; i < world1.getSize(); i++) {
            if (!world1.getWorldsList().get(i).equals(world2.getWorldsList().get(i)))
                differenceList.add(new WorldsPair(world1.getWorldsList().get(i), world2.getWorldsList().get(i)));
        }
        //todo: arrayList?!
        List<WorldsPair> cleanedDifferenceList = new LinkedList<>();
        for (WorldsPair worldsPair : differenceList) {
            if (!worldsPair.isEquivalent())
                cleanedDifferenceList.add(worldsPair);
        }

        if (cleanedDifferenceList.size() == 0)
            areEqual = true;


    }

    class WorldsPair {
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

    public boolean areEqual() {
        return areEqual;
    }

    public static void setSignature(AbstractSignature requestedSignature) {
        signature = requestedSignature;
    }

}
