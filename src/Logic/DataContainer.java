package Logic;

import java.util.List;
import java.util.Set;

public class DataContainer {
    private static List<Set<Integer>> conditonalsList;

    public static List<Set<Integer>> getCondList() {
        return conditonalsList;
    }

    public static void createWorlds(int numberOfWorlds) {
        NfcCreator nfcCreator = new NfcCreator(numberOfWorlds);
        conditonalsList = nfcCreator.getWorldsList();
    }

    public static void createConditionals(){
        //todo NfcCreator nfcCreator = new
    }
}
