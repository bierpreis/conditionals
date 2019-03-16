import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static List<List<Integer>> conditonalsList = new LinkedList<>();

    public static void main(String[] args) {


        addOneElements();
        addTwoElements();
        //addThreeElements();
        //addFourElements();

        System.out.println("Number of elements: " + conditonalsList.size());
        printList(conditonalsList);


    }


    private static void printList(List<List<Integer>> listList) {
        for (List<Integer> stringFromList : listList)
            System.out.println(stringFromList);
    }

    private static void addOneElements() {

        for (int i = 0; i < 4; i++) {
            List<Integer> listToAdd = new LinkedList<>();
            listToAdd.add(i);
            conditonalsList.add(listToAdd);

        }
        System.out.println("length with 1 elements: " + conditonalsList.size());

    }

    private static void addTwoElements() {


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                List<Integer> listToAdd = new LinkedList<>();
                listToAdd.add(i);
                listToAdd.add(j);
                conditonalsList.add(listToAdd);
            }

        }

        System.out.println("length with 2 elements: " + conditonalsList.size());
    }

    private static void addThreeElements() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 4; k++)
                    if (i != j && i != k && j != k)
                        System.out.println("lol");

        }
        System.out.println("length with 3 elements: " + conditonalsList.size());
    }

    private static void addFourElements() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 4; k++)
                    for (int l = 0; l < 4; l++)
                        if (i != j && i != k && i != l && j != k && j != l && k != l)
                            System.out.println("lol");
        }
        System.out.println("length with 4 elements: " + conditonalsList.size());
    }

    private void cleanList(){
        removeSameValues();
    }

    private void removeSameValues(){
        //for( List<Integer> intList : conditonalsList)
            //if(intList.)
    }

}
