package Logic;

import java.util.List;
import java.util.Set;

public class DataContainer {
    private static List<Set<Integer>> worldsList;
    private static List<Conditional> conditionalList;

    public static List<Set<Integer>> getWorldsList() {
        return worldsList;
    }

    public static void createWorlds(int numberOfWorlds) {
        NfcCreator nfcCreator = new NfcCreator(numberOfWorlds);
        worldsList = nfcCreator.getWorldsList();
    }

    public static void createConditionals() {
        System.out.println("trying to create conditionals");
        NfcCreator nfcCreator = new NfcCreator(4);
        worldsList = nfcCreator.getWorldsList();
        conditionalList = nfcCreator.getConditionalsList();


    }

    public static List<Conditional> getConditionalSet() {
        return conditionalList;
    }
}
