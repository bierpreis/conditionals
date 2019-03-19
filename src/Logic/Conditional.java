package Logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Conditional implements Comparable {
    private List<Integer> left;
    private Set<Integer> right;

    public Conditional() {
        left = new LinkedList<>();
        right = new TreeSet<>();
    }

    @Override
    public int compareTo(Object o) {
        Conditional other = (Conditional) o;

        if (right.size() < other.right.size())
            return -1;
        else if (right.size() > other.right.size())
            return 1;
        else {
            int thisRightSum = 0;
            int otherRightSum = 0;

            for (int thisRightInt : right) {
                thisRightSum = thisRightSum + thisRightInt;
            }
            for (int otherRightInt : other.right) {
                otherRightSum = otherRightSum + otherRightInt;
            }
            if (thisRightSum > otherRightSum)
                return -1;
            if (thisRightSum < otherRightSum)
                return 1;
        }

        if (left.size() < other.left.size())
            return -1;
        else if (left.size() > other.left.size())
            return 1;
        else {
            int thisLeftSum = 0;
            int otherLeftSum = 0;

            for (int thisRightInt : left) {
                thisLeftSum = thisLeftSum + thisRightInt;
            }
            for (int otherRightInt : other.left) {
                otherLeftSum = otherLeftSum + otherRightInt;
            }
            if (thisLeftSum > otherLeftSum)
                return -1;
            if (thisLeftSum < otherLeftSum)
                return 1;
            System.out.println("fak sth was equal!!^^^");
        }


//        if (left.size() < other.left.size())
//            return -1;
//        else if (left.size() > other.left.size())
//            return 1;
//        else if (l)*/
//        System.out.println("was not enought..");
        return 0;
    }

    @Override
    public String toString() {
        return left.toString() + " | " + right.toString();
    }

    public void setLeft(List<Integer> newList) {
        left = newList;
    }

    public void setRight(Set<Integer> newList) {
        right = newList;
    }

    public List<Integer> getLeft() {
        return left;
    }

    public Set<Integer> getRight() {
        return right;
    }
}
