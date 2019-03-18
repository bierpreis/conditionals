import java.util.*;


public class Main {

    static int maxNumberOfWorlds = 4;


    private static List<Set<Integer>> conditonalsList = new LinkedList<>();

    public static void main(String[] args) {


        addOneElements();
        addTwoElements();
        addThreeElements();
        addFourElements();

        System.out.println("Number of elements: " + conditonalsList.size());
        printList(conditonalsList);


    }


    private static void printList(List<Set<Integer>> listList) {
        for (Set<Integer> stringFromList : listList)
            System.out.println(stringFromList);
    }

    private static void addOneElements() {

        for (int i = 0; i < maxNumberOfWorlds; i++) {
            Set setToAdd = new TreeSet();
            setToAdd.add(i);

            addWorldToList(setToAdd);

        }
        System.out.println("length with 1 elements: " + conditonalsList.size());

    }

    private static void addTwoElements() {


        for (int i = 0; i < maxNumberOfWorlds; i++) {
            for (int j = 0; j < maxNumberOfWorlds; j++) {
                Set setToAdd = new TreeSet();
                setToAdd.add(i);
                setToAdd.add(j);
                if (setToAdd.size() > 1)
                    addWorldToList(setToAdd);
            }

        }

        System.out.println("length with 2 elements: " + conditonalsList.size());
    }

    private static void addThreeElements() {
        for (int i = 0; i < maxNumberOfWorlds; i++) {
            for (int j = 0; j < maxNumberOfWorlds; j++)
                for (int k = 0; k < maxNumberOfWorlds; k++) {
                    Set setToAdd = new TreeSet();
                    setToAdd.add(i);
                    setToAdd.add(j);
                    setToAdd.add(k);
                    if (setToAdd.size() > 2)
                        addWorldToList(setToAdd);
                }

        }
        System.out.println("length with 3 elements: " + conditonalsList.size());
    }

    private static void addFourElements() {
        for (int i = 0; i < maxNumberOfWorlds; i++) {
            for (int j = 0; j < maxNumberOfWorlds; j++)
                for (int k = 0; k < maxNumberOfWorlds; k++)
                    for (int l = 0; l < maxNumberOfWorlds; l++) {
                        Set setToAdd = new TreeSet();
                        setToAdd.add(i);
                        setToAdd.add(j);
                        setToAdd.add(k);
                        setToAdd.add(l);
                        addWorldToList(setToAdd);
                    }

        }
        System.out.println("length with 4 elements: " + conditonalsList.size());
    }


    private static void addWorldToList(Set setToAdd) {
        if (!conditonalsList.contains(setToAdd))
            conditonalsList.add(setToAdd);
    }
}


