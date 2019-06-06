package nfc.model;


import kb_creator.model.Conditionals.NewConditional;
import kb_creator.model.Signature.AbstractSignature;

import java.util.*;


public class NfcCreator {
    private final List<World> worlds;
    private final List<ConditionalList> cnfcEq;
    private final List<Conditional> basicConditionalList;
    private final List<Conditional> cnfc;
    private final List<Conditional> nfc;

    private final List<NewConditional> newNfc;
    private final List<NewConditional> newCnfc;

    public NfcCreator(AbstractSignature signature) {

        worlds = createWorlds(signature);

        //todo: this is not basicConditionalList because wrong oder.
        //-> correct order for basicConditionalList is in def 5 bottom
        basicConditionalList = createBasicConditionalList(worlds);

        setCounterConditionals(basicConditionalList);

        cnfcEq = createCnfcEq(basicConditionalList);
        cnfc = createCnfc(cnfcEq);

        nfc = createNfc(cnfcEq);

        newNfc = translateConditionals(nfc);


        newCnfc = translateConditionals(cnfc);
        System.out.println("basicConditionalList created");
    }


    //3 creators
    private List<World> createWorlds(AbstractSignature signature) {
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

    private List<Conditional> createNfc(List<ConditionalList> cnfc) {
        List<Conditional> nfc = new ArrayList<>();

        //add the first one of every equivalence class
        for (ConditionalList conditionalList : cnfc)
            nfc.add(conditionalList.get(0));

        //then add the rest
        for (ConditionalList conditionalList : cnfc) {
            for (int i = 1; i < conditionalList.getList().size(); i++)
                nfc.add(conditionalList.get(i));
        }

        return nfc;
    }


    private List<Conditional> createBasicConditionalList(List<World> worldsList) {

        List<Conditional> basicConditionalList = new ArrayList<>();

        for (World world : worldsList)
            basicConditionalList.addAll(createConditionalsForWorld(world));


        Collections.sort(basicConditionalList);
        //this simple numbering is just for basicConditionalList view and not really useful
        int counter = 1;
        for (Conditional conditional : basicConditionalList) {
            conditional.setNumber(counter);
            counter++;
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

    public List<Conditional> getbasicCondionals() {
        return basicConditionalList;
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

    private void setCounterConditionals(List<Conditional> nfc) {

        //todo this, then check if this works with newConditionals and then improve equals of newConditional
        for (Conditional conditional : nfc) {
            for (Conditional otherConditional : nfc) {
                if (conditional.getBasicCounterContional().equals(otherConditional))
                    conditional.setActualCounterConditional(otherConditional);
            }
        }
    }

    public List<Conditional> getNfc() {
        return nfc;
    }

}



