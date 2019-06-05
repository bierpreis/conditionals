package nfc.model;


import kb_creator.model.Conditionals.NewConditional;
import kb_creator.model.Signature.AbstractSignature;

import java.util.*;


public class NfcCreator {
    private final List<World> worlds;
    private final List<ConditionalList> cnfcEq;
    private final List<Conditional> nfc;
    private final List<Conditional> cnfc;

    private final List<NewConditional> newNfc;
    private final List<NewConditional> newCnfc;

    public NfcCreator(AbstractSignature signature) {

        worlds = createWorlds(signature);

        nfc = createNfc(worlds);

        cnfcEq = createCnfcEq(nfc);
        cnfc = createCnfc(cnfcEq);

        newNfc = translateConditionals(nfc);


        newCnfc = translateConditionals(cnfc);
        System.out.println("nfc created");
    }


    //3 creators
    public List<World> createWorlds(AbstractSignature signature) {
        World.setSignature(signature);
        int numberOfWorlds = signature.getPossibleWorlds().size();
        List<Integer> initWorldsList = new ArrayList<>();
        for (int i = numberOfWorlds - 1; i >= 0; i--) {
            initWorldsList.add(i);
        }

        List<World> worldsList = createSubSetList(initWorldsList);

        Collections.sort(worldsList);

        return worldsList;
    }


    private List<Conditional> createNfc(List<World> worldsList) {

        List<Conditional> basicConditionalList = new ArrayList<>();

        for (World world : worldsList)
            basicConditionalList.addAll(createConditionalsForWorld(world));


        Collections.sort(basicConditionalList);
        //this simple numbering is just for nfc view and not really useful
        int counter = 1;
        for (Conditional conditional : basicConditionalList) {
            conditional.setNumber(counter);
            counter++;
        }

        for (Conditional conditional : basicConditionalList) {
            conditional.setCounterConditional();
        }
        return basicConditionalList;
    }

    private List<ConditionalList> createCnfcEq(final List<Conditional> nfc) {


        List<ConditionalList> cNfc = new ArrayList<>();
        List<Conditional> alreadyAddedList = new ArrayList<>();

        //iterate basic conditionals
        for (Conditional conditionalToAdd : nfc) {
            //only create new sublist if conditional was not added before as second conditional
            if (!alreadyAddedList.contains(conditionalToAdd)) {
                ConditionalList subList = new ConditionalList();
                subList.add(conditionalToAdd.createCopy());
                //iterate over base list
                for (Conditional currentConditional : nfc) {
                    //try to find equivalent conditionals
                    if (currentConditional.isEquivalent(conditionalToAdd)) {
                        //avoid adding the same base conditionals again
                        if (!currentConditional.equals(conditionalToAdd)) {
                            subList.add(currentConditional.createCopy());
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

        List<Conditional> cnfc = new ArrayList<>();

        for (ConditionalList sublist : cnfcEq)
            cnfc.add(sublist.get(0));
        return cnfc;
    }


    //sub methods

    private List<Conditional> createConditionalsForWorld(World currentWorld) {
        List<Conditional> currentConditionalList = new ArrayList<>();

        List<Integer> currentWorldIntList = currentWorld.getWorldsList();
        List<World> allSubSetsOfCurrentWorld = createSubSetList(new ArrayList<>(currentWorldIntList));

        for (World currentSubSworld : allSubSetsOfCurrentWorld) {
            //only add real subsets not equal sets
            if (!currentSubSworld.equals(currentWorld))
                currentConditionalList.add(new Conditional(currentSubSworld, currentWorld));
        }

        return currentConditionalList;
    }


    private List<World> createSubSetList(List<Integer> inputList) {
        List<World> subSetList = new ArrayList<>();
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
        List<NewConditional> newConditionals = new ArrayList<>();

        for (Conditional oldConditional : oldConditionals) {
            NewConditional newConditional = new NewConditional(oldConditional);
            newConditional.setNumber(oldConditional.getNumber());
            newConditional.setCounterConditional(oldConditional);
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



