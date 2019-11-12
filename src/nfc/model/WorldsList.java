package nfc.model;

import kb_creator.model.propositional_logic.signature.AB;
import kb_creator.model.propositional_logic.signature.ABC;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WorldsList implements Comparable {

    private final List<Integer> worldsList;

    private static View view = View.NUMBERS; //this default is for kbcreator. maybe delete later
    private static AbstractSignature signature;

    //the number is for identification reasons and for translation into propositional formulas
    private int number;

    public WorldsList() {
        worldsList = new ArrayList<>();

    }

    public boolean isEquivalent(WorldsList otherWorldsList) {

        //if the sets are different sizes, they cant be equivalent
        if (this.getSize() != otherWorldsList.getSize())
            return false;

        //iterate trough lists and check if element pairs are equivalent
        for (int i = 0; i < this.getSize(); i++) {

            //get a pair of worlds
            if (!this.getWorldsList().get(i).equals(otherWorldsList.getWorldsList().get(i))) {
                boolean equivalent = false;

                //check if the pair is equivalent
                for (List<Integer> equivalenceGroup : signature.getEqGroups()) {
                    if ((equivalenceGroup.contains(this.getWorldsList().get(i)) && equivalenceGroup.contains(otherWorldsList.getWorldsList().get(i))))
                        equivalent = true;
                }

                //return false when the first pair is not equivalent
                if (!equivalent)
                    return false;
            }
        }

        //return true if all pairs are equivalent
        return true;

    }

    public boolean newIsEquivalent(WorldsList otherWorldsList, List<Integer> equivalenceGroup) {

        //if the sets are different sizes, they cant be equivalent
        if (this.getSize() != otherWorldsList.getSize())
            return false;

        //iterate trough lists and check if element pairs are equivalent
        for (int i = 0; i < this.getSize(); i++) {

            //get a pair of worlds
            if (!this.getWorldsList().get(i).equals(otherWorldsList.getWorldsList().get(i))) {
                boolean equivalent = false;

                //check if the pair is equivalent

                if ((equivalenceGroup.contains(this.getWorldsList().get(i)) && equivalenceGroup.contains(otherWorldsList.getWorldsList().get(i))))
                    equivalent = true;


                //return false when the first pair is not equivalent
                if (!equivalent)
                    return false;
            }
        }

        //return true if all pairs are equivalent
        return true;

    }

    //this ordering is ordering according to definition 1 and 2
    @Override
    public int compareTo(Object o) {
        WorldsList otherWorld = (WorldsList) o;

        if (worldsList.size() < otherWorld.getWorldsList().size())
            return -1;
        if (worldsList.size() > otherWorld.getWorldsList().size())
            return 1;

        for (int i = 0; i < worldsList.size(); i++) {
            if (worldsList.get(i) > otherWorld.getWorldsList().get(i))
                return -1;
            if (worldsList.get(i) < otherWorld.getWorldsList().get(i))
                return 1;

        }

        //this happens when both worlds are equal
        return 0;


    }

    public void addInt(int worldToAdd) {
        worldsList.add(worldToAdd);
        Collections.sort(worldsList, Collections.reverseOrder());
    }

    public void addList(List<Integer> newList) {
        worldsList.addAll(newList);
        Collections.sort(worldsList, Collections.reverseOrder());
    }

    @Override
    public String toString() {

        String originalString = worldsList.toString();
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
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WorldsList))
            return false;
        WorldsList worldToCompare = (WorldsList) o;


        return this.worldsList.equals(worldToCompare.getWorldsList());

    }

    public void removeWorlds(WorldsList worldsToRemove) {
        worldsList.removeAll(worldsToRemove.getWorldsList());
    }

    private String translateNumbersToLetters(String string) {

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

    public void setNumber(int number) {
        this.number = number;
    }

    public List<WorldsList> createRenamings() {
        List<WorldsList> renamingsList = null;

        if (signature instanceof AB) {
            renamingsList = new ArrayList<>(1);


            List<Integer> intList = new ArrayList<>(worldsList.size());

            for (int world : worldsList) {
                switch (world) {
                    case 0:
                        intList.add(0);
                        break;
                    case 1:
                        intList.add(2);
                        break;
                    case 2:
                        intList.add(1);
                        break;
                    case 3:
                        intList.add(3);
                        break;
                    default:
                        throw new RuntimeException("Finding equivalent WorldsList failed!");

                }
            }
            WorldsList worldsList = new WorldsList();
            worldsList.addList(intList);
            renamingsList.add(worldsList);

            //todo: check this in paper and here again. this shall not fail!
        } else if (signature instanceof ABC) {
            renamingsList = new ArrayList<>(3);

            //renaming a-b and b-a
            {
                List<Integer> intList = new ArrayList<>(worldsList.size());

                for (int world : worldsList) {
                    switch (world) {
                        case 0:
                            intList.add(0);
                            break;
                        case 1:
                            intList.add(1);
                            break;
                        case 2:
                            intList.add(4);
                            break;
                        case 3:
                            intList.add(5);
                            break;
                        case 4:
                            intList.add(2);
                            break;
                        case 5:
                            intList.add(3);
                            break;
                        case 6:
                            intList.add(6);
                            break;
                        case 7:
                            intList.add(7);
                            break;
                        default:
                            throw new RuntimeException("Finding equivalent WorldsList failed!");

                    }
                }
                WorldsList worldsList = new WorldsList();
                worldsList.addList(intList);
                renamingsList.add(worldsList);
            }


            //renaming a-c and c - a
            {
                List<Integer> intList = new ArrayList<>(worldsList.size());

                for (int world : worldsList) {
                    switch (world) {
                        case 0:
                            intList.add(0);
                            break;
                        case 1:
                            intList.add(4);
                            break;
                        case 2:
                            intList.add(2);
                            break;
                        case 3:
                            intList.add(6);
                            break;
                        case 4:
                            intList.add(1);
                            break;
                        case 5:
                            intList.add(5);
                            break;
                        case 6:
                            intList.add(3);
                            break;
                        case 7:
                            intList.add(7);
                            break;
                        default:
                            throw new RuntimeException("Finding equivalent WorldsList failed!");

                    }
                }
                WorldsList worldsList = new WorldsList();
                worldsList.addList(intList);
                renamingsList.add(worldsList);
            }


            //renaming b-c and c-b
            {
                List<Integer> intList = new ArrayList<>(worldsList.size());

                for (int world : worldsList) {
                    switch (world) {
                        case 0:
                            intList.add(0);
                            break;
                        case 1:
                            intList.add(2);
                            break;
                        case 2:
                            intList.add(1);
                            break;
                        case 3:
                            intList.add(3);
                            break;
                        case 4:
                            intList.add(4);
                            break;
                        case 5:
                            intList.add(6);
                            break;
                        case 6:
                            intList.add(5);
                            break;
                        case 7:
                            intList.add(7);
                            break;
                        default:
                            throw new RuntimeException("Finding equivalent WorldsList failed!");

                    }
                }
                WorldsList worldsList = new WorldsList();
                worldsList.addList(intList);
                renamingsList.add(worldsList);
            }


        } else throw new RuntimeException("No Valid Signature found!");


        return renamingsList;
    }

    //getters

    public List<Integer> getWorldsList() {
        return worldsList;
    }

    public int getSize() {
        return worldsList.size();
    }

    public int getNumber() {
        return number;
    }

    public AbstractSignature getSignature() {
        return signature;
    }

}
