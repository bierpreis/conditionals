package Logic;

import java.util.List;

public class DataContainer {//todo: delete this class completely?
    private static List<World> worldsList;
    private static List<Conditional> conditionalList;
    private static List<ConditionalList> cNfc;


    public static void createWorlds(int signatureAmount) {
        NfcCreator nfcCreator = new NfcCreator(signatureAmount);
        nfcCreator.createWorlds();
        worldsList = nfcCreator.getWorldsList();

    }

    public static void createConditionals(int signatureAmount) {
        NfcCreator nfcCreator = new NfcCreator(signatureAmount);
        nfcCreator.createConditionals();
        conditionalList = nfcCreator.getConditionalsList();


    }

    public static void createCnfc(int signatureAmount) {
        NfcCreator nfcCreator = new NfcCreator(signatureAmount);
        nfcCreator.createcNfc();
        cNfc = nfcCreator.getcNfc();
    }

    public static List<World> getWorldsList() {
        return worldsList;
    }

    public static List<Conditional> getConditionalSet() {
        return conditionalList;
    }

    public static List<ConditionalList> getCnfc() {
        return cNfc;
    }


}
