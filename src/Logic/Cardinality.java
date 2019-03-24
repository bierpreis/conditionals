package Logic;

public class Cardinality {
    public int leftNumber;
    public int rightNumber;

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
}
