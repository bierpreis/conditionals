package Logic;


import java.util.*;


public class NfcCreator {

    private int numberOfWorlds;

    private List<World> worldsList;
    private List<Conditional> basicConditionalList;

    private List<ConditionalList> cNfc;

    public NfcCreator(String signature) {
        numberOfWorlds = (int) Math.pow((double) 2, (double) signature.length());
    }


    // 3 getters
    public List<World> getWorldsList() {
        return worldsList;
    }

    public List<Conditional> getConditionalsList() {
        return basicConditionalList;
    }

    public List<ConditionalList> getcNfc() {
        return cNfc;
    }

    //3 creators

    public void createWorlds() {

        List<Integer> initWorldsList = new LinkedList<>();
        for (int i = numberOfWorlds - 1; i >= 0; i--) {
            initWorldsList.add(i);
        }

        worldsList = createSubSetList(initWorldsList);


        Collections.sort(worldsList);

    }

    public void createConditionals() {
        createWorlds();

        basicConditionalList = new LinkedList<>();

        for (World world : worldsList)
            createConditionalsForWorld(world);


        Collections.sort(basicConditionalList);
    }

    public void createcNfc() {
        createWorlds();
        createConditionals();

        cNfc = new LinkedList<>();
        List<Conditional> alreadyAddedList = new LinkedList<>();

        //iterate basic conditionals
        for (Conditional conditionalToAdd : basicConditionalList) {
            //only create new sublist if conditional was not added before as second conditional
            if (!alreadyAddedList.contains(conditionalToAdd)) {
                ConditionalList subList = new ConditionalList();
                subList.add(conditionalToAdd);
                //iterate over base list
                for (Conditional currentConditional : basicConditionalList) {
                    //try to find equivalent conditionals
                    if (currentConditional.isEquivalent(conditionalToAdd)) {
                        //avoid adding the same base conditionals again
                        if (!currentConditional.equals(conditionalToAdd)) {
                            subList.add(currentConditional);
                            alreadyAddedList.add(currentConditional);
                        }
                    }
                }
                cNfc.add(subList);
            }
        }
        Collections.sort(cNfc);
    }


    //sub methods
    //todo: this is taken from inet?! maybe use the other subset mehtod? or put this in world?
    public void createConditionalsForWorld(World currentWorld) {
        Set<World> listOfConditionalRights = new HashSet<>();

        Integer[] worldNumersArray = currentWorld.getWorldsList().stream().toArray(Integer[]::new);

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
                    newWorld.addInt(worldNumersArray[j]);

                    listOfConditionalRights.add(newWorld);
                }
        }
        for (World leftSide : listOfConditionalRights) {
            Conditional newConditional = new Conditional(leftSide, currentWorld);

            if (newConditional.isValid())
                basicConditionalList.add(newConditional);
        }
    }


    public static List<World> createSubSetList(List<Integer> inputList) {
        List<World> subSetList = new LinkedList<>();
        for (Integer world : inputList) {
            for (ListIterator<World> setsIterator = subSetList.listIterator(); setsIterator.hasNext(); ) {
                World newWorld = new World();
                newWorld.addList(setsIterator.next().getWorldsList());
                newWorld.addInt(world);
                setsIterator.add(newWorld);
            }
            World otherWorld = new World();
            otherWorld.addInt(world);
            subSetList.add(otherWorld);
        }
        return subSetList;
    }


}



