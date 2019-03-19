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


    public boolean equals(Conditional c) {
        if (c.getLeft().equals(left) && c.getRight().equals(right)) {
            System.out.println("found two equals!");
            //todo
        }
        return true;
    }

    public List<Integer> getLeft() {
        return left;
    }

    public Set<Integer> getRight() {
        return right;
    }
}
