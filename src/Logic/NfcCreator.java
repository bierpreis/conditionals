package Logic;


import java.util.*;


public class NfcCreator {

    private List<World> worldsList;
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


    }


    public List getWorldsList() {
        return worldsList;
    }

    public void createContiionals() {
        for (World world : worldsList)
            createConditionalsForWorld(world);
    }

    //todo: this is taken from inet?! maybe use the other subset mehtod?
    public void createConditionalsForWorld(World currentWorld) {
        Set<World> leftSideList = new HashSet<>();

        Integer[] array = currentWorld.getWorldsList().stream().toArray(Integer[]::new);

        int n = currentWorld.getWorldsList().size();

        // Run a loop for printing all 2^n
        // subsets one by one
        for (int i = 1; i < (1 << n); i++) { // i = 15 fault is here?!
            World newWorld = new World();

            // Print current subset
            for (int j = 0; j < n; j++)

                // (1<<j) is a number with jth bit 1
                // so when we 'and' them with the
                // subset number we get which numbers
                // are present in the subset and which
                // are not
                if ((i & (1 << j)) > 0) {
                    newWorld.addInt(array[j]);

                    leftSideList.add(newWorld);
                }
        }
        for (World leftSide : leftSideList) { //todo rename
            Conditional newConditional = new Conditional();
            newConditional.setLeft(leftSide);
            newConditional.setRight(currentWorld);

            if (newConditional.isValid())
                conditionalList.add(newConditional);
        }

        //System.out.println("final conditionals: ");
        //for (Conditional conditional : conditionalList) //todo do this only when creating conditionals not worlds
        //System.out.println(conditional.toString());
    }

    public List<Conditional> getConditionalsList() {
        return conditionalList;
    }

    public static List<World> createSubSetList(List<Integer> input) {
        List<World> sets = new LinkedList<>(); //todo: linked or array??
        for (Integer world : input) {
            for (ListIterator<World> setsIterator = sets.listIterator(); setsIterator.hasNext(); ) {
                World newWorld = new World();
                newWorld.addList(setsIterator.next().getWorldsList());
                newWorld.addInt(world);
                setsIterator.add(newWorld);
            }
            World otherWorld = new World();
            otherWorld.addInt(world);
            sets.add(otherWorld);
        }
        return sets;
    }


}


