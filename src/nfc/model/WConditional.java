package nfc.model;

import java.util.ArrayList;
import java.util.List;

public class WConditional implements Comparable {
    private final WorldSet antecedent;
    private final WorldSet consequence;

    private WConditional counterConditional;

    //this is needed for proper columns in conditional field
    private static int longestConditional = 0;

    private List<Integer> eqConditionalNumbers;

    private int number;
    private static String spaceFillCharacter = " ";

    public WConditional(WorldSet consequence, WorldSet antecedent) {
        this.consequence = consequence;
        this.antecedent = antecedent;
        if (this.toString().length() > longestConditional)
            longestConditional = this.toString().length() + 4; // + 4 reserves the space for the numbering for good column look
        eqConditionalNumbers = new ArrayList<>();
    }

    public boolean isEquivalent(WConditional otherConditional) {
        return consequence.isEquivalent(otherConditional.consequence) && antecedent.isEquivalent(otherConditional.antecedent);

    }

    //this is ordering according to definition 3
    @Override
    public int compareTo(Object o) {
        if (!(o instanceof WConditional))
            throw new RuntimeException("Cant compare " + o.getClass().getName() + "to Conditional");

        WConditional otherConditional = (WConditional) o;

        if (this.antecedent.compareTo(otherConditional.getAntecedent()) != 0)
            return this.antecedent.compareTo(otherConditional.getAntecedent());
        else return this.consequence.compareTo(otherConditional.getConsequence());
    }


    @Override
    public String toString() {
        String consequenceString = consequence.toString();
        String antecedentString = antecedent.toString();
        consequenceString = consequenceString.replace("},", "}");
        antecedentString = antecedentString.replace("},", "}");
        return "(" + consequenceString + " | " + antecedentString + ")";
    }


    public void setNumber(int number) {
        if (this.number != 0)
            System.out.println("Careful! Conditional number " + this.number + "will be overwritten by " + number + ". This should not be! Check conditional creation for errors!");
        this.number = number;
    }

    public static void setSpaceDot(boolean isDotActive) {
        if (isDotActive)
            spaceFillCharacter = ".";
        else spaceFillCharacter = " ";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WConditional))
            return false;
        else {
            WConditional otherConditional = (WConditional) o;
            boolean leftEquals = otherConditional.getConsequence().equals(consequence);
            boolean rightEquals = otherConditional.getAntecedent().equals(antecedent);

            //comment this out if u want to make sure equals works
            //System.out.println(equals + ": " + this + " and " + otherConditional);
            return leftEquals && rightEquals;
        }

    }

    public WConditional createCopy() {
        return new WConditional(this.consequence, this.antecedent);
    }

    //this returns a new counter conditional
    //which is used to find the actual counter conditional
    public WConditional getBasicCounterConditional() {

        WorldSet newConsequence = new WorldSet();
        newConsequence.addList(antecedent.getWorldsList());
        newConsequence.removeWorld(consequence);
        return new WConditional(newConsequence, antecedent);

    }

    //this sets the counter conditional as the real object
    public void setActualCounterConditional(WConditional counterConditional) {
        if (counterConditional == null)
            throw new RuntimeException("Null is no valid counter conditional!");
        this.counterConditional = counterConditional;

        //comment out to check correct counter conditionals
        //System.out.println("real: " + this.getNumber() + this);
        //System.out.println("counter: " + this.counterConditional.getNumber() + this.counterConditional);
    }


    public void addEqConditionalNumber(int eqNumber) {
        this.eqConditionalNumbers.add(eqNumber);
    }

    public void addEqNumbersList(List<Integer> eqConditionalList) {
            this.eqConditionalNumbers.addAll(eqConditionalList);
    }

    //getters

    public int getNumber() {
        return number;
    }

    public WConditional getActualCounterConditional() {
        if (counterConditional == null)
            throw new RuntimeException("No Counter Conditional found");
        return counterConditional;
    }


    public List<Integer> getEqList() {
        return eqConditionalNumbers;
    }

    public WorldSet getAntecedent() {
        return antecedent;
    }

    public WorldSet getConsequence() {
        return consequence;
    }

    public static int getLongestConditional() {
        return longestConditional;
    }

}
