package Logic;

import java.util.LinkedList;
import java.util.List;

public class Utility {


    public static List getEquivGroups(List<Conditional> baseList) {


        List<List<Conditional>> conditionalsSortedByNumber = createBaseGroups(baseList);


        return createEquivGroups(conditionalsSortedByNumber);
    }

    private static List<List<Conditional>> createBaseGroups(List<Conditional> baseList) {
        List<List<Conditional>> baseGroups = new LinkedList<>();

        for(Conditional conditional: baseList){

        }


        return baseGroups;
    }


    private static List<List<Conditional>> createEquivGroups(List<List<Conditional>> conditionalsSortedByNumber) {
        List<List<Conditional>> equivGroupList = new LinkedList<>();

        return equivGroupList;
    }
}
