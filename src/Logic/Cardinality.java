package Logic;

import java.util.Objects;

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

        int value = (int) (Math.pow(leftNumber, 2) * rightNumber) / 5;
        System.out.println("hashing.." + value); //todo value is always 0?!?!?! left and right numbers are 0!!!
        return value;
    }
}
