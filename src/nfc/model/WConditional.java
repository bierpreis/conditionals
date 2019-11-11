package nfc.model;

import java.util.ArrayList;
import java.util.List;

public class WConditional implements Comparable {
    private final WorldsList antecedent;
    private final WorldsList consequence;

    private WConditional counterConditional;

    private List<WConditional> eqList;

    private int number;

    //these are needed for dis play functions in nfc viewer
    private static int longestConditional = 0;
    private static String spaceFillCharacter = " ";

    public WConditional(WorldsList consequence, WorldsList antecedent) {
        this.consequence = consequence;
        this.antecedent = antecedent;
        if (this.toString().length() > longestConditional)
            longestConditional = this.toString().length() + 4; // + 4 reserves the space for the numbering for good column look

        eqList = new ArrayList<>();
    }

    //todo: this is still wrong. see examples. maybe change things around: create equivalents from a given conditional and then search for it?
    //idea: equivalence is only possible if eq group is inside. but renaming would possibly change every world except 0 and 7.
    //maybe make a rename method? rename a for b and a for c and all. every renaming would make characteristic change of worlds.
    //create eq worlds like that then search for the real conditionals?
    public boolean isEquivalent(WConditional otherConditional) {
        return newIsEquivalent(otherConditional);

        /*
         *//*        problems with signature, examples. this says they are eq, but is it true?

         ({ab!c} | {ab!c, !a!bc}) eq ({ab!c} | {ab!c, a!b!c})

        ({a!bc} | {a!bc, !ab!c}) eq ({ab!c} | {ab!c, a!b!c})

        ({!ab!c} | {a!bc, !ab!c}) eq ({a!b!c} | {ab!c, a!b!c})

        //remove? does equals induce equivalent?
        if (this.equals(otherConditional))
            return false;

            *//*


        boolean consequenceEq = consequence.isEquivalent(otherConditional.consequence);
        boolean antecedentEq = antecedent.isEquivalent(otherConditional.antecedent);


*//*        if (consequenceEq && antecedentEq)
            System.out.println(this + " eq " + otherConditional);
        *//*

        return consequenceEq && antecedentEq;*/

    }

    public boolean newIsEquivalent(WConditional otherConditional) {
        for (List<Integer> eqGroup : consequence.getSignature().getEqGroups())
            if (antecedent.newIsEquivalent(otherConditional.getAntecedent(), eqGroup) && consequence.newIsEquivalent(otherConditional.getConsequence(), eqGroup))
                return true;

        return false;
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

    public WConditional createCopy() {
        return new WConditional(this.consequence, this.antecedent);
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
        this.eqList.add(conditionalToAdd);
    }

    public void addEqList(List<WConditional> eqListToAdd) {
        this.eqList = eqListToAdd;
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


    public List<WConditional> getEqList() {
        return eqList;
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

}