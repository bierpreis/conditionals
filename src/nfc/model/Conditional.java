package nfc.model;

public class Conditional implements Comparable {
    private final World consequence;
    private final World antecedent;

    //this is needed for porper columns in conditional field
    private static int longestConditional = 0;


    private int number;
    private static String spaceFillCharacter = " ";

    public Conditional(World consequence, World antecedent) {
        this.consequence = consequence;
        this.antecedent = antecedent;
        if (this.toString().length() > longestConditional)
            longestConditional = this.toString().length() + 4; // + 4 reserves the space for the numbering for good column look
    }

    public boolean isEquivalent(Conditional otherConditional) {
        return consequence.isEquivalent(otherConditional.consequence) && antecedent.isEquivalent(otherConditional.antecedent);

    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Conditional))
            throw new RuntimeException("Cant compare " + o.getClass().getName() + "to Conditional");
        Conditional other = (Conditional) o;

        if (antecedent.getSize() < other.antecedent.getSize())
            return -1;
        if (antecedent.getSize() > other.antecedent.getSize())
            return 1;

        if (consequence.getSize() < other.consequence.getSize())
            return -1;
        if (consequence.getSize() > other.consequence.getSize())
            return 1;

        int comparedRight = compareWorldsElements(antecedent, other.antecedent);
        if (comparedRight != 0)
            return comparedRight;

        int comparedLeft = compareWorldsElements(consequence, other.consequence);
        if (comparedLeft != 0)
            return comparedLeft;

        throw new RuntimeException("Comparing Conditionals failed!");
    }


    private int compareWorldsElements(World firstWorld, World secondWorld) {
        for (int i = 0; i < firstWorld.getSize(); i++) {
            if (firstWorld.getWorldsList().get(i) > secondWorld.getWorldsList().get(i))
                return -1;
            if (firstWorld.getWorldsList().get(i) < secondWorld.getWorldsList().get(i))
                return 1;

        }
        return 0;
    }

    @Override
    public String toString() {
        String consequenceString = consequence.toString();
        String antecendString = antecedent.toString();
        consequenceString = consequenceString.replace("},", "}");
        antecendString = antecendString.replace("},", "}");
        return "(" + consequenceString + " | " + antecendString + ")";
    }


    public void setNumber(int number) {
        if (this.number != 0)
            System.out.println("number " + this.number + "will be overwritten by " + number);
        this.number = number;
    }

    public static void setSpaceDot(boolean isDotActive) {
        if (isDotActive)
            spaceFillCharacter = ".";
        else spaceFillCharacter = " ";
    }

    public Conditional getCounterConditional() {

        World newLeftWorld = new World();
        newLeftWorld.addList(antecedent.getWorldsList());
        newLeftWorld.removeWorld(consequence);
        return new Conditional(newLeftWorld, antecedent);

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
            boolean leftEquals = otherConditional.getConsequence().equals(consequence);
            boolean rightEquals = otherConditional.getAntecedent().equals(antecedent);
            return leftEquals && rightEquals;
        }

    }

    public World getAntecedent() {
        return antecedent;
    }

    public World getConsequence() {
        return consequence;
    }

    public static int getLongestConditional() {
        return longestConditional;
    }

    public Conditional createCopy() {
        return new Conditional(this.consequence, this.antecedent);
    }

}
