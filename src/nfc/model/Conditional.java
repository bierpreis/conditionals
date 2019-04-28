package nfc.model;


public class Conditional implements Comparable {
    private final World leftWorld; //todo: antecend, consequence
    private final World rightWorld;

    //this is needed for porper columns in conditional field
    private static int longestConditional = 0;

    private int number;
    private static String spaceFillCharacter = " ";

    public Conditional(World leftWorld, World rightWorld) {
        this.leftWorld = leftWorld;
        this.rightWorld = rightWorld;
        if (this.toString().length() > longestConditional)
            longestConditional = this.toString().length() + 4; // + 4 reserves the space for the numbering for good column look

    }

    public boolean isEquivalent(Conditional otherConditional) {
        return leftWorld.isEquivalent(otherConditional.leftWorld) && rightWorld.isEquivalent(otherConditional.rightWorld);

    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Conditional))
            throw new RuntimeException("Cant compare " + o.getClass().getName() + "to Conditional");
        Conditional other = (Conditional) o;

        if (rightWorld.getSize() < other.rightWorld.getSize())
            return -1;
        if (rightWorld.getSize() > other.rightWorld.getSize())
            return 1;

        if (leftWorld.getSize() < other.leftWorld.getSize())
            return -1;
        if (leftWorld.getSize() > other.leftWorld.getSize())
            return 1;

        int comparedRight = compareWorldsElements(rightWorld, other.rightWorld);
        if (comparedRight != 0)
            return comparedRight;

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

        //this is to calculate whitespaces for colums in cnfc nfc.model.view
        int numberOfSpacesToAdd = 0;
        if (stringToReturn.length() < longestConditional)
            numberOfSpacesToAdd = (longestConditional - stringToReturn.length());
        for (int i = 0; i < numberOfSpacesToAdd; i++)
            stringToReturn = stringToReturn + spaceFillCharacter;

        return stringToReturn;
    }


    public void setNumber(int number) {
        this.number = number;
    }

    public static void setSpaceDot(boolean isDotActive) {
        if (isDotActive)
            spaceFillCharacter = ".";
        else spaceFillCharacter = " ";
    }

    public Conditional getCounterConditional() {

        World newLeftWorld = new World();
        newLeftWorld.addList(rightWorld.getWorldsList());
        newLeftWorld.removeWorld(leftWorld);
        return new Conditional(newLeftWorld, rightWorld);

    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Conditional))
            return false;
        else {
            Conditional otherConditional = (Conditional) o;
            boolean leftEquals = otherConditional.getLeftWorld().equals(leftWorld);
            boolean rightEquals = otherConditional.getRightWorld().equals(rightWorld);
            return leftEquals && rightEquals;
        }

    }

    public World getRightWorld() {
        return rightWorld;
    }

    public World getLeftWorld() {
        return leftWorld;
    }

}
