package Logic;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Utility {


    public static Map getEquivGroups(List<Conditional> baseList) {


        Map<Integer, Conditional> conditionalsSortedByNumber = createBaseGroups(baseList); //todo: map of list of conditionals?!


        return conditionalsSortedByNumber; //todo return real groups
    }

    private static Map createBaseGroups(List<Conditional> baseList) {
        Map<Integer, Conditional> cardinalityMap = new LinkedHashMap<>();

        for (Conditional conditional : baseList) {
            cardinalityMap.put(conditional.getKey(), conditional);

        }


        return cardinalityMap;
    }


    private static List<List<Conditional>> createEquivGroups(List<List<Conditional>> conditionalsSortedByNumber) {
        List<List<Conditional>> equivGroupList = new LinkedList<>();

        return equivGroupList;
    }
}
