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
        else if (left.size() < other.left.size())
            return -1;
        else if (left.size() > other.left.size())
            return 1;
        else System.out.println("2 were same size!");
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
