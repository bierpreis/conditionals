package Logic;

import java.util.*;

public class Conditional implements Comparable {
    private World leftWorld;
    private World rightWorld;

    public Conditional() {
        leftWorld = new World();
        rightWorld = new World();
    }

    @Override
    public int compareTo(Object o) {
        Conditional other = (Conditional) o;

        if (rightWorld.getSize() < other.rightWorld.getSize())
            return -1;
        if (rightWorld.getSize() > other.rightWorld.getSize())
            return 1;

        int comparedRight = compareWorldsElements(rightWorld, other.rightWorld);
        if (comparedRight != 0)
            return comparedRight;

        if (leftWorld.getSize() < other.leftWorld.getSize())
            return -1;
        if (leftWorld.getSize() > other.leftWorld.getSize())
            return 1;

        int comparedLeft = compareWorldsElements(leftWorld, other.leftWorld);
        if (comparedLeft != 0)
            return comparedLeft;

        System.out.println("comparing conditionals failed!!!");
        return 0;
    }


    private int compareWorldsElements(List<Integer> firstList, List<Integer> secondList) {
        for (int i = 0; i < firstList.size(); i++) {
            if (firstList.get(i) > secondList.get(i))
                return -1;
            if (firstList.get(i) < secondList.get(i))
                return 1;

        }
        return 0;
    }

    @Override
    public String toString() {
        return leftWorld.toString() + " | " + rightWorld.toString();
    }

    public void setLeft(World newWorld) {
        leftWorld = newWorld;
    }

    public void setRight(World newWorld) {
        rightWorld = newWorld;
    }

    public World getLeft() {
        return leftWorld;
    }

    public World getRight() {
        return rightWorld;
    }


}
