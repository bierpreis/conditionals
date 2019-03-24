package Logic;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DataContainer {//todo: delete this class completely?
    private static List<World> worldsList;
    private static List<Conditional> conditionalList;
    private static Map<Cardinality, Conditional> cNfcMap;

    public static List<World> getWorldsList() {
        return worldsList;
    }

    public static void createWorlds(int signatureAmount) {
        NfcCreator nfcCreator = new NfcCreator(signatureAmount);
        worldsList = nfcCreator.getWorldsList();
        Collections.sort(worldsList);
    }

    public static void createConditionals(int signatureAmount) {
        NfcCreator nfcCreator = new NfcCreator(signatureAmount);
        worldsList = nfcCreator.getWorldsList();
        conditionalList = nfcCreator.getConditionalsList();
        Collections.sort(conditionalList);


    }

    public static void createCnfc(int signatureAmount) {
        NfcCreator nfcCreator = new NfcCreator(signatureAmount);
        cNfcMap = Utility.getEquivGroups(nfcCreator.getConditionalsList());
    }

    public static Map getCnfc() {
        return cNfcMap;
    }

    public static List<Conditional> getConditionalSet() {
        return conditionalList;
    }

}
