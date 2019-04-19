package nfc.model;


import java.util.*;


public class NfcCreator {
    private List<World> worlds;
    private List<ConditionalList> cnfcEq;
    private List<Conditional> nfc;
    private List<Conditional> cnfc;

    public NfcCreator(String signature) {

        //todo: signature
        worlds = createWorlds(signature);
        cnfcEq = createCnfcEq(signature);
        cnfc = createCnfc(signature);
        nfc = createNfc(signature);
    }

    //todo: remove
    private void setOptions(HashMap<String, String> options) {
        for (String option : options.keySet()) {
            if (option.equals("nfc/view"))
                World.setView(options.get(option));

            if (option.equals("numbering"))
                Conditional.setNumbeing(options.get(option));

            if (option.equals("space"))
                Conditional.setSpaceDot(options.get(option));

        }
    }

    //3 creators

    public List<World> createWorlds(String signature) {
        World.setSignature(signature);
        int numberOfWorlds = (int) Math.pow((double) 2, (double) signature.length());
        List<Integer> initWorldsList = new LinkedList<>();
        for (int i = numberOfWorlds - 1; i >= 0; i--) {
            initWorldsList.add(i);
        }

        List<World> worldsList = createSubSetList(initWorldsList);

        Collections.sort(worldsList);

        return worldsList;
    }

    //todo: this is called 4 times?!
    public List<Conditional> createNfc(String signature) {
        List<World> worldsList = createWorlds(signature);

        List<Conditional> basicConditionalList = new LinkedList<>();

        for (World world : worldsList)
            basicConditionalList.addAll(createConditionalsForWorld(world));


        Collections.sort(basicConditionalList);

        int counter = 1;
        for (Conditional conditional : basicConditionalList) {
            conditional.setNumber(counter);
            counter++;
        }

        System.out.println(basicConditionalList.size());
        return basicConditionalList;
    }

    public List<ConditionalList> createCnfcEq(String signature) {
        //createWorlds(signature);
        List<Conditional> basicConditionalList = createNfc(signature);

        List<ConditionalList> cNfc = new LinkedList<>();
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

        return cNfc;
    }


    //sub methods

    private List<Conditional> createConditionalsForWorld(World currentWorld) {
        List<Conditional> currentConditionalList = new LinkedList<>();

        List<Integer> currentWorldIntList = currentWorld.getWorldsList();
        List<World> allSubSetsOfCurrentWorld = createSubSetList(new LinkedList(currentWorldIntList));

        for (World currentSubSworld : allSubSetsOfCurrentWorld) {
            //only add real subsets not equal sets
            if (!currentSubSworld.equals(currentWorld))
                currentConditionalList.add(new Conditional(currentSubSworld, currentWorld));
        }

        return currentConditionalList;
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

    public List<Conditional> createCnfc(String signature) {
        List<ConditionalList> cnfcEq = createCnfcEq(signature);

        List<Conditional> cnfc = new LinkedList<>();

        for (ConditionalList sublist : cnfcEq)
            cnfc.add(sublist.get(0));

        return cnfc;
    }


    public List<Conditional> getCnfc() {
        return cnfc;
    }

    public List<Conditional> getNfc() {
        return nfc;
    }

}



