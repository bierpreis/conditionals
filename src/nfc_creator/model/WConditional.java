package nfc_creator.model;

import java.util.ArrayList;
import java.util.List;

public class WConditional implements Comparable {
    private final WorldsList antecedent;
    private final WorldsList consequence;

    private WConditional counterConditional;


    //this list will contain equal but not the actual eq conditionals and will be null later
    //it is only used for creating the conditionals
    private List<WConditional> basicEqList;

    //this list contains the actual eq conditionals
    private List<WConditional> realEqList;

    private int number;

    //these are needed for dis play functions in nfc viewer
    private static int longestConditional = 0;
    private static String spaceFillCharacter = " ";

    public WConditional(WorldsList consequence, WorldsList antecedent) {
        this.consequence = consequence;
        this.antecedent = antecedent;
        if (this.toString().length() > longestConditional)
            longestConditional = this.toString().length() + 4; // + 4 reserves the space for the numbering for good column look


        realEqList = new ArrayList<>();
    }


    public boolean isEquivalent(WConditional otherConditional) {
        for (WConditional eqConditional : getBasicEqList())
            if (otherConditional.equals(eqConditional))
                return true;

        return false;
    }


    private void createBasicEquivalents() {
        basicEqList = new ArrayList<>();
        List<WorldsList> antecedentList = antecedent.createRenamings();
        List<WorldsList> consequenceList = consequence.createRenamings();


        for (int i = 0; i < antecedentList.size(); i++) {
            WConditional possibleEqConditional = new WConditional(consequenceList.get(i), antecedentList.get(i));

            //dont add the conditional to itselfs eq conditionals
            if (!this.equals(possibleEqConditional))
                basicEqList.add(possibleEqConditional);
        }


    }

    //this is ordering according to definition 3
    @Override
    public int compareTo(Object o) {
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

    //this returns a new counter conditional
    //which is used to find the actual counter conditional
    public WConditional getBasicCounterConditional() {

        WorldsList newConsequence = new WorldsList();
        newConsequence.addList(antecedent.getWorldsList());
        newConsequence.removeWorlds(consequence);
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


    public void addEqConditional(WConditional conditionalToAdd) {
        this.realEqList.add(conditionalToAdd);
    }

    public void addEqList(List<WConditional> eqListToAdd) {
        this.realEqList = eqListToAdd;
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


    public List<WConditional> getRealEqList() {
        return realEqList;
    }

    public WorldsList getAntecedent() {
        return antecedent;
    }

    public WorldsList getConsequence() {
        return consequence;
    }

    public static int getLongestConditional() {
        return longestConditional;
    }

    public List<WConditional> getBasicEqList() {

        //not nice but this cant be in constructor it would be stack overflow
        if (basicEqList == null)
            createBasicEquivalents();
        return basicEqList;
    }
}
