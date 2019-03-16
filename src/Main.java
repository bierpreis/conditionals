import java.util.LinkedList;
import java.util.List;

public class Main {

    private static List<String> conditonalsList = new LinkedList<>();

    public static void main(String[] args) {


        addOneElements();
        addTwoElements();

        printList(conditonalsList);

    }


    private static void printList(List<String> stringList) {
        for (String stringFromList : stringList)
            System.out.println(stringFromList);
    }

    private static void addOneElements() {
        for (int i = 0; i < 4; i++) {
            conditonalsList.add(Integer.toString(i));

        }


    }

    private static void addTwoElements() {
        String worldToAdd = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++)

                conditonalsList.add(Integer.toString(i) + ", " + j);

        }
    }

}
