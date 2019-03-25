package Logic;

import java.util.LinkedList;
import java.util.List;

public class Cardinality {

    private int leftNumber;
    private int rightNumber;

    public Cardinality(int leftNumber, int rightNumber) {
        this.leftNumber = leftNumber;
        this.rightNumber = rightNumber;
    }

    public int getLeft() {
        return leftNumber;
    }

    public int getRight() {
        return rightNumber;
    }

    @Override
    public boolean equals(Object o) {
        Cardinality other = (Cardinality) o;
        return (leftNumber == other.getLeft() && rightNumber == other.getRight());
    }

    @Override
    public int hashCode() { //todo: create working hash method

        String stringHash = Integer.toString(leftNumber) + Integer.toString(rightNumber);
        System.out.println("stringhash" + stringHash);
        return Integer.parseInt(stringHash);
    }

    @Override
    public String toString() {
        return leftNumber + " " + rightNumber;
    }
}
