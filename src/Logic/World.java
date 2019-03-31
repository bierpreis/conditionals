package Logic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class World implements Comparable {

    private static String signature = "ab";

    List<Integer> worlds;

    List<Integer> equivalenceGroup = Arrays.asList(2, 1);

    List<Integer> equivalenceGroup1 = Arrays.asList(6, 5, 3);
    List<Integer> equivalenceGroup2 = Arrays.asList(4, 2, 1);

    public World() {
        worlds = new LinkedList<>();
    }

    public boolean isEquivalent(World otherWorld) {
        WorldDiffrence worldDiffrence = new WorldDiffrence(this, otherWorld, signature);
        return worldDiffrence.areEqual();

    }

    public static void setSignature(String requestedSignature) {
        signature = requestedSignature;
    }


    @Override
    public int compareTo(Object o) {
        World otherWorld = (World) o;

        if (worlds.size() < otherWorld.getWorldsList().size())
            return -1;
        if (worlds.size() > otherWorld.getWorldsList().size())
            return 1;

        for (int i = 0; i < worlds.size(); i++) {
            if (worlds.get(i) > otherWorld.getWorldsList().get(i))
                return -1;
            if (worlds.get(i) < otherWorld.getWorldsList().get(i))
                return 1;

        }
        System.out.println("comparing worlds failed!!! (" + this.toString() + " and " + otherWorld.toString() + " )");
        return 0;


    }


    public List<Integer> getWorldsList() {
        return worlds;
    }

    public int getSize() {
        return worlds.size();
    }

    public void addInt(int worldToAdd) {
        worlds.add(worldToAdd);
    }

    public void addList(List<Integer> newList) {
        for (Integer newInt : newList)
            worlds.add(newInt);
    }

    @Override
    public String toString() {
        return worlds.toString();
    }
}
