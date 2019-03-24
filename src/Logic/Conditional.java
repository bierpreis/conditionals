package Logic;

public class Conditional implements Comparable {
    private World leftWorld;
    private World rightWorld;

    private Cardinality cardinality;

    public Conditional() {
        leftWorld = new World();
        rightWorld = new World();
        cardinality = new Cardinality(leftWorld.getSize(), rightWorld.getSize());
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

        System.out.println("comparing failed!!!" + this.toString() + " and " + other.toString());
        return 0;
    }


    private int compareWorldsElements(World firstWolrd, World secondWorld) {
        for (int i = 0; i < firstWolrd.getSize(); i++) {
            if (firstWolrd.getWorldsList().get(i) > secondWorld.getWorldsList().get(i))
                return -1;
            if (firstWolrd.getWorldsList().get(i) < secondWorld.getWorldsList().get(i))
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

    public boolean isValid() {
        return rightWorld.getSize() > leftWorld.getSize();
    }

    public Cardinality getCardinality() {
        return cardinality;
    }


}
