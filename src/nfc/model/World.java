package nfc.model;

import kb_creator.model.propositional_logic.signature.AB;
import kb_creator.model.propositional_logic.signature.ABC;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.ArrayList;
import java.util.List;

public class World implements Comparable {

    private final List<Integer> WORLDS_LIST;

    private static View view = View.NUMBERS; //this default is for kbcreator. maybe delete later
    private static AbstractSignature signature;

    //the number is for identification reasons and for translation into propositional formulas
    private int number;


    public World() {
        WORLDS_LIST = new ArrayList<>();

    }

    public boolean isEquivalent(World otherWorld) {
        WorldDifference worldDifference = new WorldDifference(this, otherWorld);
        return worldDifference.areEqual();

    }

    //this ordering is ordering according to definition 1 and 2
    @Override
    public int compareTo(Object o) {
        World otherWorld = (World) o;

        if (WORLDS_LIST.size() < otherWorld.getWorldsList().size())
            return -1;
        if (WORLDS_LIST.size() > otherWorld.getWorldsList().size())
            return 1;

        for (int i = 0; i < WORLDS_LIST.size(); i++) {
            if (WORLDS_LIST.get(i) > otherWorld.getWorldsList().get(i))
                return -1;
            if (WORLDS_LIST.get(i) < otherWorld.getWorldsList().get(i))
                return 1;

        }

        //this happens when both worlds are equal
        return 0;


    }


    public List<Integer> getWorldsList() {
        return WORLDS_LIST;
    }

    public int getSize() {
        return WORLDS_LIST.size();
    }

    public void addInt(int worldToAdd) {
        WORLDS_LIST.add(worldToAdd);
    }

    public void addList(List<Integer> newList) {
        WORLDS_LIST.addAll(newList);
    }

    @Override
    public String toString() {

        String originalString = WORLDS_LIST.toString();
        originalString = originalString.replace('[', '{');
        originalString = originalString.replace(']', '}');

        if (view.equals(View.LETTERS))
            originalString = translateNumbersToLetters(originalString);


        return originalString;

    }


    public static void setView(View requestedView) {
        view = requestedView;
    }

    public static void setSignature(AbstractSignature requestedSignature) {
        signature = requestedSignature;
        WorldDifference.setSignature(requestedSignature);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof World))
            return false;
        World worldToCompare = (World) o;


        return this.WORLDS_LIST.equals(worldToCompare.getWorldsList());

    }

    public void removeWorld(World worldsToRemove) {
        WORLDS_LIST.removeAll(worldsToRemove.getWorldsList());
    }

    public String translateNumbersToLetters(String string) {

        if (signature instanceof ABC) {

            string = string.replace("0", "!a!b!c");
            string = string.replace("1", "!a!bc");
            string = string.replace("2", "!ab!c");
            string = string.replace("3", "!abc");
            string = string.replace("4", "a!b!c");
            string = string.replace("5", "a!bc");
            string = string.replace("6", "ab!c");
            string = string.replace("7", "abc");

        } else if (signature instanceof AB) {
            string = string.replace("0", "!a!b");
            string = string.replace("1", "!ab");
            string = string.replace("2", "a!b");
            string = string.replace("3", "ab");


        } else throw new RuntimeException("Invalid signature: " + signature);

        return string;
    }

    public enum View {
        NUMBERS, LETTERS
    }

    public AbstractSignature getSignature() {
        return signature;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

}
