package Logic;


import java.util.*;


public class NfcCreator {

    private List<World> worldsList;
    private List<Conditional> conditionalList;

    public NfcCreator(int signatureAmount) {
        initWorlds(signatureAmount);

        for (World world : worldsList)
            createConditionalsForWorld(world);
    }


    public List getWorldsList() {
        return worldsList;
    }

    public void initWorlds(int signatureAmount) {
        int numberOfWorlds = (int) Math.pow((double) 2, (double) signatureAmount);
        conditionalList = new LinkedList<>();

        List<Integer> initWorldsList = new LinkedList<>();
        for (int i = numberOfWorlds - 1; i >= 0; i--) {
            initWorldsList.add(i);
        }

        worldsList = createSubSetList(initWorldsList);


    }


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
                conditionalList.add(newConditional);
        }
    }

    public List<Conditional> getConditionalsList() {
        return conditionalList;
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

    public List<List<Conditional>> createcNfc() {
        List<List<Conditional>> cNfc = new LinkedList<>();
        for (Conditional firstElementInList : conditionalList) {
            List<Conditional> currentConditionalList = new LinkedList<>();
            currentConditionalList.add(firstElementInList);
            for (Conditional currentConditional : conditionalList) {
                if (currentConditional.isEquivalent(firstElementInList))
                    currentConditionalList.add(currentConditional);
            }
            cNfc.add(currentConditionalList);
        }
        //todo: sort sublists
        return cNfc;
    }
}



