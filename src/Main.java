import gui.MainWindow;

import java.util.*;


public class Main {

    static int maxNumberOfWorlds = 6;


    private static List<Set<Integer>> conditonalsList = new LinkedList<>();

    public static void main(String[] args) {




        addOneElements();

        if (maxNumberOfWorlds > 1)
            addTwoElements();
        if (maxNumberOfWorlds > 2)
            addThreeElements();
        if (maxNumberOfWorlds > 3)
            addFourElements();
        if (maxNumberOfWorlds > 4)
            addFiveElements();
        if (maxNumberOfWorlds > 5)
            addSixElements();
        if (maxNumberOfWorlds > 6)
            addSevenElements();
        if (maxNumberOfWorlds > 7)
            addEightElements();

        System.out.println("Number of elements: " + conditonalsList.size());
        printList(conditonalsList);

        new MainWindow(conditonalsList);


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

    private static void addFiveElements() {
        for (int i = 0; i < maxNumberOfWorlds; i++) {
            for (int j = 0; j < maxNumberOfWorlds; j++)
                for (int k = 0; k < maxNumberOfWorlds; k++)
                    for (int l = 0; l < maxNumberOfWorlds; l++)
                        for (int m = 0; m < maxNumberOfWorlds; m++) {
                            Set setToAdd = new TreeSet();
                            setToAdd.add(i);
                            setToAdd.add(j);
                            setToAdd.add(k);
                            setToAdd.add(l);
                            setToAdd.add(m);
                            addWorldToList(setToAdd);
                        }

        }
        System.out.println("length with 5 elements: " + conditonalsList.size());
    }

    private static void addSixElements() {
        for (int i = 0; i < maxNumberOfWorlds; i++) {
            for (int j = 0; j < maxNumberOfWorlds; j++)
                for (int k = 0; k < maxNumberOfWorlds; k++)
                    for (int l = 0; l < maxNumberOfWorlds; l++)
                        for (int m = 0; m < maxNumberOfWorlds; m++)
                            for (int n = 0; n < maxNumberOfWorlds; n++) {
                                Set setToAdd = new TreeSet();
                                setToAdd.add(i);
                                setToAdd.add(j);
                                setToAdd.add(k);
                                setToAdd.add(l);
                                setToAdd.add(m);
                                setToAdd.add(n);
                                addWorldToList(setToAdd);
                            }

        }
        System.out.println("length with 6 elements: " + conditonalsList.size());
    }

    private static void addSevenElements() {
        for (int i = 0; i < maxNumberOfWorlds; i++) {
            for (int j = 0; j < maxNumberOfWorlds; j++)
                for (int k = 0; k < maxNumberOfWorlds; k++)
                    for (int l = 0; l < maxNumberOfWorlds; l++)
                        for (int m = 0; m < maxNumberOfWorlds; m++)
                            for (int n = 0; n < maxNumberOfWorlds; n++)
                                for (int o = 0; o < maxNumberOfWorlds; o++) {
                                    Set setToAdd = new TreeSet();
                                    setToAdd.add(i);
                                    setToAdd.add(j);
                                    setToAdd.add(k);
                                    setToAdd.add(l);
                                    setToAdd.add(m);
                                    setToAdd.add(n);
                                    setToAdd.add(o);
                                    addWorldToList(setToAdd);
                                }

        }
        System.out.println("length with 7 elements: " + conditonalsList.size());
    }

    private static void addEightElements() {
        for (int i = 0; i < maxNumberOfWorlds; i++) {
            for (int j = 0; j < maxNumberOfWorlds; j++)
                for (int k = 0; k < maxNumberOfWorlds; k++)
                    for (int l = 0; l < maxNumberOfWorlds; l++)
                        for (int m = 0; m < maxNumberOfWorlds; m++)
                            for (int n = 0; n < maxNumberOfWorlds; n++)
                                for (int o = 0; o < maxNumberOfWorlds; o++)
                                    for (int p = 0; p < maxNumberOfWorlds; p++) {
                                        Set setToAdd = new TreeSet();
                                        setToAdd.add(i);
                                        setToAdd.add(j);
                                        setToAdd.add(k);
                                        setToAdd.add(l);
                                        setToAdd.add(m);
                                        setToAdd.add(n);
                                        setToAdd.add(o);
                                        setToAdd.add(p);
                                        addWorldToList(setToAdd);
                                    }

        }
        System.out.println("length with 8 elements: " + conditonalsList.size());
    }


    private static void addWorldToList(Set setToAdd) {
        if (!conditonalsList.contains(setToAdd))
            conditonalsList.add(setToAdd);
    }
}


