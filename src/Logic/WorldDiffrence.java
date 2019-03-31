package Logic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WorldDiffrence {
    private List<Integer> equivalenceGroup = Arrays.asList(2, 1);

    private List<Integer> equivalenceGroup1 = Arrays.asList(6, 5, 3);
    private List<Integer> equivalenceGroup2 = Arrays.asList(4, 2, 1);

    private static String signature;
    private World world1;
    private World world2;

    private List<WorldsPair> diffrenceList;

    private boolean areEqual;

    public WorldDiffrence(World world1, World world2) {
        this.world1 = world1;
        this.world2 = world2;

        diffrenceList = new LinkedList<>();
        areEqual = false;

        calculateDiffrence();
    }

    private void calculateDiffrence() {
        if (world1.getSize() != world2.getSize())
            return;
        for (int i = 0; i < world1.getSize(); i++) {
            if (world1.getWorldsList().get(i) != world2.getWorldsList().get(i))
                diffrenceList.add(new WorldsPair(world1.getWorldsList().get(i), world2.getWorldsList().get(i)));
        }

        List<WorldsPair> cleanedDiffrenceList = new LinkedList<>();
        for (WorldsPair worldsPair : diffrenceList) {
            if (!worldsPair.isEquivalent()) {
                cleanedDiffrenceList.add(worldsPair);

            }
        }

        if (cleanedDiffrenceList.size() == 0) {
            areEqual = true;
            return;
        }


    }

    class WorldsPair {
        int firstInt;
        int secondInt;

        public WorldsPair(int firstInt, int secondInt) {
            this.firstInt = firstInt;
            this.secondInt = secondInt;
        }

        public boolean isEquivalent() {
            if (signature.equals("ab"))
                return (equivalenceGroup.contains(firstInt) && equivalenceGroup.contains(secondInt));


            if (signature.equals("abc")) {
                if (equivalenceGroup1.contains(firstInt) && equivalenceGroup1.contains(secondInt))
                    return true;
                else return (equivalenceGroup2.contains(firstInt) && equivalenceGroup2.contains(secondInt));
            }

            System.out.println("wrong signature set!!!");
            return false;
        }


    }

    public boolean areEqual() {
        return areEqual;
    }

    public static void setSignature(String requestedSignature) {
        signature = requestedSignature;
    }

}
