import java.util.LinkedList;
import java.util.List;

public class Main {

    private static List<String> conditonalsList = new LinkedList<>();

    public static void main(String[] args) {


        for (int i = 0; i < 4; i++) {
            conditonalsList.add(Integer.toString(i));

        }

        printList(conditonalsList);

    }



    private static void printList(List<String> stringList) {
        for (String stringFromList : stringList)
            System.out.println(stringFromList);
    }

}
