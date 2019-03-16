import java.util.LinkedList;
import java.util.List;

public class Main {

    private static List<String> conditonalsList = new LinkedList<>();

    public static void main(String[] args) {


        addOneElements();
        addTwoElements();
        addThreeElements();
        addFourElements();

        System.out.println("Number of elements: " + conditonalsList.size());
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
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++)

                conditonalsList.add(Integer.toString(i) + ", " + j);

        }
    }

    private static void addThreeElements() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 4; k++)

                    conditonalsList.add(Integer.toString(i) + ", " + j + ", " + k);

        }
    }

    private static void addFourElements() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 4; k++)
                    for (int l = 0; l < 4; l++)
                        conditonalsList.add(Integer.toString(i) + ", " + j + ", " + k + ", " + l);

        }
    }

}
