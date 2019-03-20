package Logic;


import java.util.*;


public class NfcCreator {

    private List<Set<Integer>> worldsList = new LinkedList<>();
    private List<Conditional> conditionalList;

    public NfcCreator(String signature) {
        conditionalList = new LinkedList<>();

        if (signature.equals("ab")) {
            addOneElements(4);
            addTwoElements(4);
            addThreeElements(4);
        }

        if (signature.equals("abc")) {
            addOneElements(8);
            addTwoElements(8);
            addThreeElements(8);
            addFourElements(8);
            addFiveElements(8);
            addSixElements(8);
            addSevenElements(8);
            addEightElements(8);
        }


        System.out.println("Number of worlds: " + (worldsList.size() +1));
        //printList(worldsList);

        for (Set<Integer> world : worldsList)
            createConditionals(world);


    }


    private void printList(List<Set<Integer>> listList) {
        for (Set<Integer> stringFromList : listList)
            System.out.println(stringFromList);
    }

    private void addOneElements(int maxNumberOfWorlds) {

        for (int i = 0; i < maxNumberOfWorlds; i++) {
            Set setToAdd = new TreeSet();
            setToAdd.add(i);

            addWorldToList(setToAdd);

        }
        System.out.println("length with 1 elements: " + worldsList.size());

    }

    private void addTwoElements(int maxNumberOfWorlds) {


        for (int i = 0; i < maxNumberOfWorlds; i++) {
            for (int j = 0; j < maxNumberOfWorlds; j++) {
                Set setToAdd = new TreeSet(Comparator.reverseOrder());
                setToAdd.add(i);
                setToAdd.add(j);
                if (setToAdd.size() > 1)
                    addWorldToList(setToAdd);
            }

        }

        System.out.println("length with 2 elements: " + worldsList.size());
    }

    private void addThreeElements(int maxNumberOfWorlds) {
        for (int i = 0; i < maxNumberOfWorlds; i++) {
            for (int j = 0; j < maxNumberOfWorlds; j++)
                for (int k = 0; k < maxNumberOfWorlds; k++) {
                    Set setToAdd = new TreeSet(Comparator.reverseOrder());
                    setToAdd.add(i);
                    setToAdd.add(j);
                    setToAdd.add(k);
                    if (setToAdd.size() > 2)
                        addWorldToList(setToAdd);
                }

        }
        System.out.println("length with 3 elements: " + worldsList.size());
    }

    private void addFourElements(int maxNumberOfWorlds) {
        for (int i = 0; i < maxNumberOfWorlds; i++) {
            for (int j = 0; j < maxNumberOfWorlds; j++)
                for (int k = 0; k < maxNumberOfWorlds; k++)
                    for (int l = 0; l < maxNumberOfWorlds; l++) {
                        Set setToAdd = new TreeSet(Comparator.reverseOrder());
                        setToAdd.add(i);
                        setToAdd.add(j);
                        setToAdd.add(k);
                        setToAdd.add(l);
                        addWorldToList(setToAdd);
                    }

        }
        System.out.println("length with 4 elements: " + worldsList.size());
    }

    private void addFiveElements(int maxNumberOfWorlds) {
        for (int i = 0; i < maxNumberOfWorlds; i++) {
            for (int j = 0; j < maxNumberOfWorlds; j++)
                for (int k = 0; k < maxNumberOfWorlds; k++)
                    for (int l = 0; l < maxNumberOfWorlds; l++)
                        for (int m = 0; m < maxNumberOfWorlds; m++) {
                            Set setToAdd = new TreeSet(Comparator.reverseOrder());
                            setToAdd.add(i);
                            setToAdd.add(j);
                            setToAdd.add(k);
                            setToAdd.add(l);
                            setToAdd.add(m);
                            addWorldToList(setToAdd);
                        }

        }
        System.out.println("length with 5 elements: " + worldsList.size());
    }

    private void addSixElements(int maxNumberOfWorlds) {
        for (int i = 0; i < maxNumberOfWorlds; i++) {
            for (int j = 0; j < maxNumberOfWorlds; j++)
                for (int k = 0; k < maxNumberOfWorlds; k++)
                    for (int l = 0; l < maxNumberOfWorlds; l++)
                        for (int m = 0; m < maxNumberOfWorlds; m++)
                            for (int n = 0; n < maxNumberOfWorlds; n++) {
                                Set setToAdd = new TreeSet(Comparator.reverseOrder());
                                setToAdd.add(i);
                                setToAdd.add(j);
                                setToAdd.add(k);
                                setToAdd.add(l);
                                setToAdd.add(m);
                                setToAdd.add(n);
                                addWorldToList(setToAdd);
                            }

        }
        System.out.println("length with 6 elements: " + worldsList.size());
    }

    private void addSevenElements(int maxNumberOfWorlds) {
        for (int i = 0; i < maxNumberOfWorlds; i++) {
            for (int j = 0; j < maxNumberOfWorlds; j++)
                for (int k = 0; k < maxNumberOfWorlds; k++)
                    for (int l = 0; l < maxNumberOfWorlds; l++)
                        for (int m = 0; m < maxNumberOfWorlds; m++)
                            for (int n = 0; n < maxNumberOfWorlds; n++)
                                for (int o = 0; o < maxNumberOfWorlds; o++) {
                                    Set setToAdd = new TreeSet(Comparator.reverseOrder());
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
        System.out.println("length with 7 elements: " + worldsList.size());
    }

    private void addEightElements(int maxNumberOfWorlds) {
        for (int i = 0; i < maxNumberOfWorlds; i++) {
            for (int j = 0; j < maxNumberOfWorlds; j++)
                for (int k = 0; k < maxNumberOfWorlds; k++)
                    for (int l = 0; l < maxNumberOfWorlds; l++)
                        for (int m = 0; m < maxNumberOfWorlds; m++)
                            for (int n = 0; n < maxNumberOfWorlds; n++)
                                for (int o = 0; o < maxNumberOfWorlds; o++)
                                    for (int p = 0; p < maxNumberOfWorlds; p++) {
                                        Set setToAdd = new TreeSet(Comparator.reverseOrder());
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
        System.out.println("length with 8 elements: " + worldsList.size());
    }


    private void addWorldToList(Set setToAdd) {
        if (!worldsList.contains(setToAdd))
            worldsList.add(setToAdd);
    }

    public List getWorldsList() {
        return worldsList;
    }

    //todo: this is taken from inet?!
    public void createConditionals(Set<Integer> worlds) {
        Set<List<Integer>> leftSideList = new HashSet<>();

        Integer[] array = worlds.stream().toArray(Integer[]::new);

        int n = worlds.size();

        // Run a loop for printing all 2^n
        // subsets one by obe
        for (int i = 1; i < (1 << n); i++) {
            List<Integer> worldsList = new LinkedList<>();

            // Print current subset
            for (int j = 0; j < n; j++)

                // (1<<j) is a number with jth bit 1
                // so when we 'and' them with the
                // subset number we get which numbers
                // are present in the subset and which
                // are not
                if ((i & (1 << j)) > 0) {
                    worldsList.add(array[j]);

                    leftSideList.add(worldsList);
                }
        }
        for (List<Integer> leftSide : leftSideList) {
            Conditional newConditional = new Conditional();
            newConditional.setLeft(leftSide);
            newConditional.setRight(worlds);
            //todo
            if (newConditional.getRight().size() > newConditional.getLeft().size())
                conditionalList.add(newConditional);
        }

        //System.out.println("final conditionals: ");
        //for (Conditional conditional : conditionalList)
        //System.out.println(conditional.toString());
    }

    public List<Conditional> getConditionalsList() {
        return conditionalList;
    }


}


