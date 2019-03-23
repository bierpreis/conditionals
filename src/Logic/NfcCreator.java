package Logic;


import java.util.*;


public class NfcCreator {

    private List<List<Integer>> worldsList;
    private List<Conditional> conditionalList;

    public NfcCreator(int number) {
        int numberOfWorlds = (int) Math.pow((double) 2, (double) number);
        conditionalList = new LinkedList<>();

        List<Integer> initWorldsList = new LinkedList<>();
        for (int i = numberOfWorlds - 1; i >= 0; i--) {
            initWorldsList.add(i);
        }
        worldsList = createSubSetList(initWorldsList);

        System.out.println("Number of worlds: " + (worldsList.size() + 1));

        for (List<Integer> world : worldsList)
            createConditionals(world);


    }


    public List getWorldsList() {
        return worldsList;
    }

    //todo: this is taken from inet?! maybe use the other subset mehtod?
    public void createConditionals(List<Integer> worlds) {
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

    public static List<List<Integer>> createSubSetList(List<Integer> input) {
        List<List<Integer>> sets = new ArrayList<>();
        for (Integer world : input) {
            for (ListIterator<List<Integer>> setsIterator = sets.listIterator(); setsIterator.hasNext(); ) {
                List<Integer> newSet = new ArrayList<>(setsIterator.next());
                newSet.add(world);
                setsIterator.add(newSet);
            }
            sets.add(new ArrayList<>(Arrays.asList(world)));
        }
        return sets;
    }


}


