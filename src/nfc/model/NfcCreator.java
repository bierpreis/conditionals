package nfc.model;


import kb_creator.model.Conditionals.NewConditional;
import kb_creator.model.Signature.AbstractSignature;

import java.util.*;


public class NfcCreator {
    private List<World> worlds;
    private List<ConditionalList> cnfcEq;
    private List<Conditional> nfc;
    private List<Conditional> cnfc;

    private List<NewConditional> newNfc;
    private List<NewConditional> newCnfc;

    public NfcCreator(AbstractSignature signature) {

        worlds = createWorlds(signature);
        nfc = createNfc(worlds);
        cnfcEq = createCnfcEq(nfc);
        cnfc = createCnfc(cnfcEq);

        newNfc = translateConditionals(nfc);
        newCnfc = translateConditionals(cnfc);

    }

    //3 creators

    public List<World> createWorlds(AbstractSignature signature) {
        World.setSignature(signature);
        int numberOfWorlds = (int) Math.pow((double) 2, (double) signature.getClass().getSimpleName().length()); //todo: fix bug here
        List<Integer> initWorldsList = new LinkedList<>();
        for (int i = numberOfWorlds - 1; i >= 0; i--) {
            initWorldsList.add(i);
        }

        List<World> worldsList = createSubSetList(initWorldsList);

        Collections.sort(worldsList);

        return worldsList;
    }


    private List<Conditional> createNfc(List<World> worldsList) {

        List<Conditional> basicConditionalList = new LinkedList<>();

        for (World world : worldsList)
            basicConditionalList.addAll(createConditionalsForWorld(world));


        Collections.sort(basicConditionalList);
        //this simple numbering is just for nfc view and not really useful
        int counter = 1;
        for (Conditional conditional : basicConditionalList) {
            conditional.setNumber(counter);
            counter++;
        }

        return basicConditionalList;
    }

    private List<ConditionalList> createCnfcEq(List<Conditional> nfc) {


        List<ConditionalList> cNfc = new LinkedList<>();
        List<Conditional> alreadyAddedList = new LinkedList<>();

        //iterate basic conditionals
        for (Conditional conditionalToAdd : nfc) {
            //only create new sublist if conditional was not added before as second conditional
            if (!alreadyAddedList.contains(conditionalToAdd)) {
                ConditionalList subList = new ConditionalList();
                subList.add(conditionalToAdd);
                //iterate over base list
                for (Conditional currentConditional : nfc) {
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
        for (ConditionalList conditionalList : cNfc) {
            conditionalList.setNumberToFirstConditional(counter);
            counter++;
        }
        for (ConditionalList conditionalList : cNfc) {
            conditionalList.setNumbersToEquivalentConditionals(counter);
            counter = conditionalList.getHighestConditionalNumber();
        }
        return cNfc;
    }

    private List<Conditional> createCnfc(List<ConditionalList> cnfcEq) {

        List<Conditional> cnfc = new LinkedList<>();

        for (ConditionalList sublist : cnfcEq)
            cnfc.add(sublist.get(0));
        return cnfc;
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


    //getters

    public List<Conditional> getCnfc() {
        return cnfc;
    }

    public List<Conditional> getNfc() {
        return nfc;
    }

    public List<ConditionalList> getCnfcEq() {
        return cnfcEq;
    }

    public List<World> getWorlds() {
        return worlds;
    }

    private List<NewConditional> translateConditionals(List<Conditional> oldConditionals) {
        List<NewConditional> newConditionals = new LinkedList<>();

        for (Conditional oldConditional : oldConditionals) {
            NewConditional newConditional = new NewConditional(oldConditional);
            newConditional.createCounterConditional(oldConditional);
            newConditional.setNumber(oldConditional.getNumber());
            newConditionals.add(newConditional);

        }
        return newConditionals;
    }

    public List<NewConditional> getNewNfc() {
        return newNfc;
    }

    public List<NewConditional> getNewCnfc() {
        return newCnfc;
    }

}



