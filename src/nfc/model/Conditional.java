package nfc.model;

public class Conditional implements Comparable {
    private final World consequence;
    private final World antecedent;

    private static boolean isNumberingActive;

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
        String consequenceString = consequence.toString();
        String antecendString = antecedent.toString();
        consequenceString = consequenceString.replace("},", "}");
        antecendString = antecendString.replace("},", "}");
        String stringToReturn = "(" + consequenceString + " | " + antecendString + ")";

        if (isNumberingActive)
            stringToReturn = number + stringToReturn;

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

    public static void setNumbersActive(boolean areNunbersRequested) {
        isNumberingActive = areNunbersRequested;
    }

}
