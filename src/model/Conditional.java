package model;

public class Conditional implements Comparable {
    private final World leftWorld;
    private final World rightWorld;
    static boolean numberMode = false;

    private int number;

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
        String leftWorldString = leftWorld.toString();
        String rightWorldString = rightWorld.toString();
        leftWorldString = leftWorldString.replace("},", "}");
        rightWorldString = rightWorldString.replace("},", "}");
        String stringToReturn = "(" + leftWorldString + " | " + rightWorldString + ")";
        if (numberMode)
            stringToReturn = number + ": " + stringToReturn;
        return stringToReturn;
    }


    public boolean isValid() {
        return rightWorld.getSize() > leftWorld.getSize();
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public static void setNumberMode(boolean requestedNumberMode) {
        numberMode = requestedNumberMode;
    }

    public static boolean getNumberMode(){ //todo: put this else where
        return numberMode;
    }

}
