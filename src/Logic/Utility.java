package Logic;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Utility {


    public static Map getEquivGroups(List<Conditional> baseList) {


        Map<Integer, List<Conditional>> conditionalsSortedByNumber = createBaseGroups(baseList);


        return conditionalsSortedByNumber; //todo return real groups
    }

    private static Map createBaseGroups(List<Conditional> baseList) {
        Map<Integer, List<Conditional>> cardinalityMap = new LinkedHashMap<>();

        for (Conditional conditional : baseList) {
            if (!cardinalityMap.containsKey(conditional.getKey())) {
                List<Conditional> conditionalList = new LinkedList();
                conditionalList.add(conditional);
                cardinalityMap.put(conditional.getKey(), conditionalList);

            } else {
                cardinalityMap.get(conditional.getKey()).add(conditional);

            }

        }


        return cardinalityMap;
    }


    private static List<List<Conditional>> createEquivGroups(List<List<Conditional>> conditionalsSortedByNumber) {
        List<List<Conditional>> equivGroupList = new LinkedList<>();

        return equivGroupList;
    }
}
