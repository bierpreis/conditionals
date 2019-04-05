package model;

import java.util.List;

public class DataContainer {//todo: delete this class completely?

    private static List<Conditional> conditionalList;
    private static List<ConditionalList> cNfc;




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
