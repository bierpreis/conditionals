package Logic;

import java.util.Collections;
import java.util.List;

public class DataContainer {//todo: delete this class completely?
    private static List<World> worldsList;
    private static List<Conditional> conditionalList;
    private static List<ConditionalList> cNfc;

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
        cNfc = nfcCreator.createcNfc();
    }

    public static List<ConditionalList> getCnfc() {
        return cNfc;
    }

    public static List<Conditional> getConditionalSet() {
        return conditionalList;
    }

}
