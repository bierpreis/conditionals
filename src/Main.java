import com.intellij.util.containers.SortedList;
import com.twelvemonkeys.util.LinkedSet;

import java.util.*;

public class Main {

    static class ListComparator implements Comparator<Set<?>> {

        @Override
        public int compare(Set<?> o1, Set<?> o2) {
            return Integer.valueOf(o1.size()).compareTo(o2.size());
        }
    }

    private static List<Set<Integer>> conditonalsList = new SortedList(new ListComparator());

    public static void main(String[] args) {


        addOneElements();
        addTwoElements();
        addThreeElements();
        //addFourElements();

        System.out.println("Number of elements: " + conditonalsList.size());
        printList(conditonalsList);
        //conditonalsList.sort(new ListComparator());
        // printList(conditonalsList);


    }


    private static void printList(List<Set<Integer>> listList) {
        for (Set<Integer> stringFromList : listList)
            System.out.println(stringFromList);
    }

    private static void addOneElements() {

        for (int i = 0; i < 4; i++) {
            Set setToAdd = new LinkedSet();
            setToAdd.add(i);

            conditonalsList.add(setToAdd);

        }
        System.out.println("length with 1 elements: " + conditonalsList.size());

    }

    private static void addTwoElements() {


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Set setToAdd = new TreeSet();
                setToAdd.add(i);
                setToAdd.add(j);
                if (setToAdd.size() > 1)
                    conditonalsList.add(setToAdd);
            }

        }

        System.out.println("length with 2 elements: " + conditonalsList.size());
    }

    private static void addThreeElements() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 4; k++) {
                    Set setToAdd = new TreeSet();
                    setToAdd.add(i);
                    setToAdd.add(j);
                    setToAdd.add(k);
                    if (setToAdd.size() > 2)
                        conditonalsList.add(setToAdd);
                }

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

    private void cleanList() {
        removeSameValues();
    }

    private void removeSameValues() {
        //for( List<Integer> intList : conditonalsList)
        //if(intList.)
    }

}
