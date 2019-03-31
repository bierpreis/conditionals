package Logic;

public class Conditional implements Comparable {
    private final World leftWorld;
    private final World rightWorld;

    public Conditional(World leftWorld, World rightWorld) {
        this.leftWorld = leftWorld;
        this.rightWorld = rightWorld;
    }

    public boolean isEquivalent(Conditional otherConditional) {
        return leftWorld.isEquivalent(otherConditional.leftWorld) && rightWorld.isEquivalent(otherConditional.rightWorld);

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


    private int compareWorldsElements(World firstWotld, World secondWorld) {
        for (int i = 0; i < firstWotld.getSize(); i++) {
            if (firstWotld.getWorldsList().get(i) > secondWorld.getWorldsList().get(i))
                return -1;
            if (firstWotld.getWorldsList().get(i) < secondWorld.getWorldsList().get(i))
                return 1;

        }
        return 0;
    }

    @Override
    public String toString() {
        return leftWorld.toString() + " | " + rightWorld.toString();
    }


    public boolean isValid() {
        return rightWorld.getSize() > leftWorld.getSize();
    }

}
