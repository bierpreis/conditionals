package Logic;

import java.util.Collections;
import java.util.List;

public class DataContainer {
    private static List<List<Integer>> worldsList;
    private static List<Conditional> conditionalList;

    public static List<List<Integer>> getWorldsList() {
        return worldsList;
    }

    public static void createWorlds(int signatureAmount) {
        NfcCreator nfcCreator = new NfcCreator(signatureAmount); //todo
        worldsList = nfcCreator.getWorldsList();
    }

    public static void createConditionals(int signatureAmount) {
        NfcCreator nfcCreator = new NfcCreator(signatureAmount); //todo
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
