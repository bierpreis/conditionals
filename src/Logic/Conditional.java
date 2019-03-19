package Logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Conditional {
    private List<Integer> left;
    private Set<Integer> right;

    public Conditional() {
        left = new LinkedList<>();
        right = new TreeSet<>();
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
}
