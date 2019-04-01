package Logic;

import java.util.LinkedList;
import java.util.List;

public class World implements Comparable {

    private final List<Integer> worlds;
    private static String signature;

    private static boolean lettersViewMode = false;


    public World() {
        worlds = new LinkedList<>();

    }

    public boolean isEquivalent(World otherWorld) {
        WorldDifference worldDiffrence = new WorldDifference(this, otherWorld);
        return worldDiffrence.areEqual();

    }

    @Override
    public int compareTo(Object o) {
        World otherWorld = (World) o;

        if (worlds.size() < otherWorld.getWorldsList().size())
            return -1;
        if (worlds.size() > otherWorld.getWorldsList().size())
            return 1;

        for (int i = 0; i < worlds.size(); i++) {
            if (worlds.get(i) > otherWorld.getWorldsList().get(i))
                return -1;
            if (worlds.get(i) < otherWorld.getWorldsList().get(i))
                return 1;

        }
        System.out.println("comparing worlds failed!!! (" + this.toString() + " and " + otherWorld.toString() + " )");
        return 0;


    }


    public List<Integer> getWorldsList() {
        return worlds;
    }

    public int getSize() {
        return worlds.size();
    }

    public void addInt(int worldToAdd) {
        worlds.add(worldToAdd);
    }

    public void addList(List<Integer> newList) {
        for (Integer newInt : newList)
            worlds.add(newInt);
    }

    @Override
    public String toString() {
        String letterString = "";
        if (!lettersViewMode) {
            String originalString = worlds.toString();
            String stringWithSetBrackets = originalString.replace('[', '{');
            stringWithSetBrackets = stringWithSetBrackets.replace(']', '}');

            return stringWithSetBrackets;

        } else {
            if (signature.equals("abc"))
                for (int i = 0; i < worlds.toString().length(); i++) {
                    char currentChar = worlds.toString().charAt(i);
                    switch (currentChar) {
                        case '0':
                            letterString += "{!a!b!c}, ";
                            break;
                        case '1':
                            letterString += "{!a!bc}, ";
                            break;
                        case '2':
                            letterString += "{!ab!c}, ";
                            break;
                        case '3':
                            letterString += "{!abc}, ";
                            break;
                        case '4':
                            letterString += "{a!b!c}, ";
                            break;
                        case '5':
                            letterString += "{a!bc}, ";
                            break;
                        case '6':
                            letterString += "{ab!c}, ";
                            break;
                        case '7':
                            letterString += "{abc}, ";
                            break;
                        default: //todo: delete??
                            System.out.println("error in to String:" + currentChar);
                    }
                }
            if (signature.equals("ab"))
                for (int i = 0; i < worlds.toString().length(); i++) {
                    char currentChar = worlds.toString().charAt(i);
                    switch (currentChar) {
                        case '0':
                            letterString = "{!a!b}, ";
                            break;
                        case '1':
                            letterString = "{!ab}, ";
                            break;
                        case '2':
                            letterString = "{a!b}, ";
                            break;
                        case '3':
                            letterString = "{ab}, ";
                            break;
                        default: //todo: delete??
                            System.out.println("error in toString: " + currentChar);
                    }
                }


            return letterString;
        }
    }

    public static void setLettersMode(String actionCommand) {
        if (actionCommand.equals("letters"))
            lettersViewMode = true;
        if (actionCommand.equals("numbers"))
            lettersViewMode = false;
    }

    public static void setSignature(String requestedSignature){
        signature = requestedSignature;
    }
}
