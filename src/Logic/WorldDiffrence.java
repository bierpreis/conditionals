package Logic;

import java.util.LinkedList;
import java.util.List;

public class WorldDiffrence {
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




        for (WorldsPair worldsPair : diffrenceList) {
            if (worldsPair.isEquivalent())
                diffrenceList.remove(worldsPair);
        }

        if (diffrenceList.size() == 0) {
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
            if ((firstInt == 1 && secondInt == 2) || (firstInt == 2 && secondInt == 1))
                return true;
            else return false;
        }


    }

    public boolean areEqual() {
        return areEqual;
    }

}
