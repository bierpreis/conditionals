package Logic;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DataContainer {
    private static List<Set<Integer>> worldsList;
    private static List<Conditional> conditionalList;

    public static List<Set<Integer>> getWorldsList() {
        return worldsList;
    }

    public static void createWorlds(String signature) {
        NfcCreator nfcCreator = new NfcCreator(signature);
        worldsList = nfcCreator.getWorldsList();
    }

    public static void createConditionals(String signature) {
        NfcCreator nfcCreator = new NfcCreator(signature);
        worldsList = nfcCreator.getWorldsList();
        conditionalList = nfcCreator.getConditionalsList();
        sortConditionalList();


    }

    public static List<Conditional> getConditionalSet() {
        return conditionalList;
    }

    private static void sortConditionalList() {
        Collections.sort(conditionalList);
    }
}
