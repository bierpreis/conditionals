import java.util.Collections;
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

        Collections.sort(conditonalsList);
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
        System.out.println("length with 1 elements: " + conditonalsList.size());

    }

    private static void addTwoElements() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                if (i != j)
                    conditonalsList.add(i + ", " + j);

        }
        System.out.println("length with 2 elements: " + conditonalsList.size());
    }

    private static void addThreeElements() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 4; k++)
                    if (i != j && i != k && j != k)
                        conditonalsList.add(i + ", " + j + ", " + k);

        }
        System.out.println("length with 3 elements: " + conditonalsList.size());
    }

    private static void addFourElements() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 4; k++)
                    for (int l = 0; l < 4; l++)
                        if (i != j && i != k && i != l && j != k && j != l && k != l)
                            conditonalsList.add(i + ", " + j + ", " + k + ", " + l);

        }
        System.out.println("length with 4 elements: " + conditonalsList.size());
    }

}
