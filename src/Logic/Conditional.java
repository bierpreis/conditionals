package Logic;

import java.util.*;

public class Conditional implements Comparable {
    private List<Integer> left;
    private List<Integer> right;

    public Conditional() {
        left = new LinkedList<>();
        right = new LinkedList<>();
    }

    @Override
    public int compareTo(Object o) {
        Conditional other = (Conditional) o;

        if (right.size() < other.right.size())
            return -1;
        if (right.size() > other.right.size())
            return 1;

        int comparedRight = compareWorlds(right, other.right);
        if (comparedRight < 0)
            return comparedRight;
        if (comparedRight > 0)
            return comparedRight;

        if (left.size() < other.left.size())
            return -1;
        if (left.size() > other.left.size())
            return 1;

        int comparedLeft = compareWorlds(left, other.left);
        if (comparedLeft < 0)
            return comparedLeft;
        if (comparedLeft > 0)
            return comparedLeft;
        System.out.println("comparing conditionals failed!!!");
        return 0;
    }


    private int compareWorlds(List<Integer> firstList, List<Integer> secondList) {
        for (int i = 0; i < firstList.size(); i++) {
            //todo: are return values correct?
            if (firstList.get(i) < secondList.get(i))
                return -1;
            if (firstList.get(i) > secondList.get(i))
                return 1;

        }
        return 0;
    }

    @Override
    public String toString() {
        return left.toString() + " | " + right.toString();
    }

    public void setLeft(List<Integer> newList) {
        left = newList;
    }

    public void setRight(Set<Integer> newSet) {
        List<Integer> newList = new LinkedList<>();
        newList.addAll(newSet);
        right = newList;
    }

    public List<Integer> getLeft() {
        return left;
    }

    public List<Integer> getRight() {
        return right;
    }


}
