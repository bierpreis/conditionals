package model;


import java.util.*;


public class NfcCreator {

    private int numberOfWorlds;

    private List<Conditional> basicConditionalList;

    private List<ConditionalList> cNfc;

    public NfcCreator(HashMap options) {
        setOptions(options);
    }

    private void setOptions(HashMap<String, String> options) {
        for (String option : options.keySet()) {
            if (option.equals("signature")) {
                World.setSignature(options.get(option));
                numberOfWorlds = (int) Math.pow((double) 2, (double) options.get(option).length());
            }

            if (option.equals("view"))
                World.setView(options.get(option));

            if (option.equals("numbering"))
                Conditional.setNumbeing(options.get(option));

            if (option.equals("space"))
                Conditional.setSpaceDot(options.get(option));

        }
    }


    // 3 getters

    public List<Conditional> getConditionalsList() {
        return basicConditionalList;
    }

    public List<ConditionalList> getcNfc() {
        return cNfc;
    }

    //3 creators

    public List<World> createWorlds(String signature) {
        int numberOfWorlds = (int) Math.pow((double) 2, (double) signature.length());
        List<Integer> initWorldsList = new LinkedList<>();
        for (int i = numberOfWorlds - 1; i >= 0; i--) {
            initWorldsList.add(i);
        }

        List<World> worldsList = createSubSetList(initWorldsList);

        Collections.sort(worldsList);

        return worldsList;
    }

    public void createConditionals(String signature) {
        List<World> worldsList = createWorlds(signature);

        basicConditionalList = new LinkedList<>();

        for (World world : worldsList)
            createConditionalsForWorld(world);


        Collections.sort(basicConditionalList);

        int counter = 1;
        for (Conditional conditional : basicConditionalList) {
            conditional.setNumber(counter);
            counter++;
        }
    }

    public void createcNfc(String signature) {
        createWorlds(signature);
        createConditionals(signature);

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
        int counter = 1;
        if (Conditional.getNumbering().equals("on")) {
            for (ConditionalList conditionalList : cNfc) {
                conditionalList.setNumberToFirstConditional(counter);
                counter++;
            }
            for (ConditionalList conditionalList : cNfc) {
                conditionalList.setNumbersToEquivalentConditionals(counter - 1);
                counter = counter + conditionalList.getHighestConditionalNumber();
            }
        }
    }


    //sub methods
    //todo: this is taken from inet?! maybe use the other subset mehtod? or put this in world?
    private void createConditionalsForWorld(World currentWorld) {
        Set<World> listOfConditionalRights = new HashSet<>();

        Integer[] worldNumbersArray = currentWorld.getWorldsList().stream().toArray(Integer[]::new);

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
                    newWorld.addInt(worldNumbersArray[j]);

                    listOfConditionalRights.add(newWorld);
                }
        }
        for (World leftSide : listOfConditionalRights) {
            Conditional newConditional = new Conditional(leftSide, currentWorld);

            if (newConditional.isValid())
                basicConditionalList.add(newConditional);
        }
    }


    private List<World> createSubSetList(List<Integer> inputList) {
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



