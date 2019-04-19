package nfc.model;

import java.util.LinkedList;
import java.util.List;

public class World implements Comparable {

    private final List<Integer> worlds;
    private static String signature;

    private static String view = "numbers";


    public World() {
        worlds = new LinkedList<>();

    }

    public boolean isEquivalent(World otherWorld) {
        WorldDifference worldDiffrence = new WorldDifference(this, otherWorld);
        return worldDiffrence.areEqual();

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
    //todo: put this in conditional panel or seperate class?
    @Override
    public String toString() {

        String originalString = worlds.toString();
        originalString = originalString.replace('[', '{');
        originalString = originalString.replace(']', '}');


        return originalString;

    }


    public static void setView(String requestedView) {
        view = requestedView;
    }

    public static void setSignature(String requestedSignature) {
        signature = requestedSignature;
        WorldDifference.setSignature(requestedSignature);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof World))
            return false;
        World worldToCompare = (World) o;


        return this.worlds.equals(worldToCompare.getWorldsList());

    }

    public void removeWorld(World worldsToRemove) {
        worlds.removeAll(worldsToRemove.getWorldsList());
    }

}
