package nfc.model;


import kb_creator.model.propositional_logic.AbstractFormula;
import kb_creator.model.propositional_logic.PConditional;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.*;

//todo: structure methods
public class NfcCreator {
    private final List<World> worldList;
    private final List<ConditionalList> oldCnfcEq;
    private final List<WConditional> wConditionalList;
    private final List<WConditional> oldCnfc;
    private final List<WConditional> oldNfc;

    private final List<PConditional> newNfc;
    private final List<PConditional> newCnfc;

    private final Map<Integer, PConditional> newNfcMap;

    private final ConditionalTranslator conditionalTranslator;

    public NfcCreator(AbstractSignature signature) {
        System.out.println("started oldNfc creator");
        worldList = createWorlds(signature);

        conditionalTranslator = new ConditionalTranslator(signature);

        //this is basic conditional list in order from definition  2
        wConditionalList = createBasicConditionalList(worldList);


        oldCnfcEq = createCnfcEq(wConditionalList);

        //this is in order on def 5.1
        oldCnfc = createCnfc(oldCnfcEq);

        //this is in order of definition 5.2
        oldNfc = createNfc(oldCnfcEq);

        setEquivalentListToNfc(oldCnfcEq);

        //this method takes much time
        setCounterConditionals(oldNfc);


        newNfc = translateConditionals(oldNfc);

        newCnfc = new ArrayList<>(oldCnfc.size());
        //add the conditionals from nfc instead of translating this again
        //because translating would create NEW conditionals
        //e.g. for settingEquivalentLists to set the lists for both nfc and nfceq
        for (int i = 0; i < oldCnfc.size(); i++)
            newCnfc.add(newNfc.get(i));

        newNfcMap = createNfcMap(newNfc);

        setEquivalentListToPConditionals(oldNfc, newNfcMap);
        System.out.println("oldNfc creator finished");
    }


    private List<World> createWorlds(AbstractSignature signature) {
        World.setSignature(signature);
        int numberOfWorlds = signature.getPossibleWorlds().size();
        List<Integer> initWorldsList = new ArrayList<>();
        for (int i = numberOfWorlds - 1; i >= 0; i--) {
            initWorldsList.add(i);
        }

        List<World> worldsList = createSubSetList(initWorldsList);

        Collections.sort(worldsList);

        for (int i = 1; i <= worldsList.size(); i++)
            worldsList.get(i - 1).setNumber(i);


        return worldsList;
    }

    private List<WConditional> createNfc(List<ConditionalList> cnfc) {
        System.out.println("creating oldNfc");
        List<WConditional> nfc = new ArrayList<>();

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


    private List<WConditional> createBasicConditionalList(List<World> worldsList) {
        System.out.println("creating basic conditionals");
        List<WConditional> basicConditionalList = new ArrayList<>();

        for (World world : worldsList)
            basicConditionalList.addAll(createConditionalsForWorld(world));


        Collections.sort(basicConditionalList);
        //this simple numbering is just for basicConditionalList view and not really useful
        int counter = 1;
        for (WConditional conditional : basicConditionalList) {
            conditional.setNumber(counter);
            counter++;
        }

        return basicConditionalList;
    }

    private List<ConditionalList> createCnfcEq(final List<WConditional> basicConditionalList) {
        System.out.println("creating oldCnfc eq");

        List<ConditionalList> cNfc = new ArrayList<>();
        List<WConditional> alreadyAddedList = new ArrayList<>();

        //iterate basic conditionals
        for (WConditional conditionalToAdd : basicConditionalList) {
            //only create new sublist if conditional was not added before as second conditional
            if (!alreadyAddedList.contains(conditionalToAdd)) {
                ConditionalList subList = new ConditionalList();
                subList.add(conditionalToAdd.createCopy());
                //iterate over base list
                for (WConditional currentConditional : basicConditionalList) {
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

        int nextConditionalNumber = 1;

        //set numbers to the first in every equivalence class
        for (ConditionalList conditionalList : cNfc) {
            conditionalList.setNumberToFirstConditional(nextConditionalNumber);
            nextConditionalNumber++;
        }

        //set numbers to all but first in equivalence class
        for (ConditionalList conditionalList : cNfc) {
            conditionalList.setNumbersToEquivalentConditionals(nextConditionalNumber);
            nextConditionalNumber = conditionalList.getNextConditionalNumber();
        }
        return cNfc;
    }

    private void setEquivalentListToNfc(List<ConditionalList> cnfcEq) {
        for (ConditionalList conditionalList : cnfcEq) {

            //collect all equivalent conditionals into the first in the eq class
            for (int i = 0; i < conditionalList.getList().size(); i++)
                conditionalList.get(0).addEqConditionalNumber(conditionalList.get(i).getNumber());


            //add eq list to all but the first in the conditional list
            for (int j = 1; j < conditionalList.getList().size(); j++)
                conditionalList.get(j).addEqNumbersList(conditionalList.get(0).getEqList());
        }
    }

    private List<WConditional> createCnfc(List<ConditionalList> cnfcEq) {
        System.out.println("creating oldCnfc");
        List<WConditional> cnfc = new ArrayList<>(cnfcEq.size());

        for (ConditionalList sublist : cnfcEq)
            cnfc.add(sublist.get(0));
        return cnfc;
    }


    private List<WConditional> createConditionalsForWorld(World currentWorld) {
        List<WConditional> currentConditionalList = new ArrayList<>();

        List<Integer> currentWorldIntList = currentWorld.getWorldsList();
        List<World> allSubSetsOfCurrentWorld = createSubSetList(new ArrayList<>(currentWorldIntList));

        for (World currentSubSworld : allSubSetsOfCurrentWorld) {
            //only add real subsets not equal sets
            if (!currentSubSworld.equals(currentWorld))
                currentConditionalList.add(new WConditional(currentSubSworld, currentWorld));
        }

        return currentConditionalList;
    }

    //this adds the numbers of equivalent conditionals to every new nfc conditional
    //this list is needed to reduce the possible candidates when initialising candidate pairs
    private void setEquivalentListToPConditionals(List<WConditional> oldNfc, Map<Integer, PConditional> newNfcMap) {
        for (WConditional oldConditional : oldNfc) {
            List<PConditional> tempEqList = new ArrayList<>(oldConditional.getEqList().size());
            for (int eqNumber : oldConditional.getEqList())
                tempEqList.add(this.newNfcMap.get(eqNumber));
            newNfcMap.get(oldConditional.getNumber()).setEqList(tempEqList);
        }
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

    
    private List<PConditional> translateConditionals(List<WConditional> wConditionalList) {
        System.out.println("translating conditionals");
        List<PConditional> pConditionalList = new ArrayList<>(wConditionalList.size());

        for (WConditional oldConditional : wConditionalList) {
            PConditional pConditional = conditionalTranslator.transLate(oldConditional);

            pConditional.setCounterConditional(conditionalTranslator.transLate(oldConditional.getActualCounterConditional()));
            pConditionalList.add(pConditional);

        }
        System.out.println("setting basic counter conditionals");
        //this takes the basic counter conditional and replaces it with the actual counter conditional reference
        //this saves some memory space, but not very much.
        for (PConditional conditional : pConditionalList) {
            for (PConditional possibleCounterConditional : pConditionalList) {
                if (conditional.getCounterConditional().equals(possibleCounterConditional))
                    conditional.setCounterConditional(possibleCounterConditional);
            }
        }
        System.out.println("finished setting basic counter conditionals");
        //comment out the following 2 lines and you can see if the counter conditionals are set correct
        //for (NewConditional conditional : newConditionals) 
        //   System.out.println("org: " + conditional.getNumber() + " counter: " + conditional.findCounterConditional().getNumber());
        //4 elements are double fucked!


        return pConditionalList;
    }


    private void setCounterConditionals(List<WConditional> nfc) {
        System.out.println("creating counter conditionals");
        long startTime = System.currentTimeMillis();
        //this takes very long. but is maybe the only way to set the counter conditional numbers?
        for (WConditional conditional : nfc) {
            conditional.setActualCounterConditional(findCounterConditional(conditional, nfc));
        }
        System.out.println("time for setting counter conditionals: " + (System.currentTimeMillis() - startTime) / 1000);
    }

    private WConditional findCounterConditional(WConditional conditional, List<WConditional> nfc) {
        for (WConditional possibleCounterConditional : nfc)
            if (conditional.getBasicCounterConditional().equals(possibleCounterConditional))
                return possibleCounterConditional;

        return null;
    }

    private Map<Integer, PConditional> createNfcMap(Collection<PConditional> nfc) {
        Map<Integer, PConditional> conditionalMap = new HashMap<>(nfc.size());
        for (PConditional conditional : nfc) {
            if (conditionalMap.containsKey(conditional.getNumber())) {
                throw new RuntimeException("Double conditional detected!");
            }
            conditionalMap.put(conditional.getNumber(), conditional);
        }

        //make it unmodifiable so no accidentally changed to this map can happen
        return Collections.unmodifiableMap(conditionalMap);
    }

    //getters

    public List<PConditional> getNewNfc() {
        return newNfc;
    }

    public List<PConditional> getNewCnfc() {
        return newCnfc;
    }

    public List<WConditional> getOldNfc() {
        return oldNfc;
    }

    public List<WConditional> getOldCnfc() {
        return oldCnfc;
    }

    public List<WConditional> getWConditionalList() {
        return wConditionalList;
    }

    public List<ConditionalList> getOldCnfcEq() {
        return oldCnfcEq;
    }

    public List<World> getWorldList() {
        return worldList;
    }

    public Map<World, AbstractFormula> getWorldsFormulasMap() {
        Map<World, AbstractFormula> mapToReturn = new HashMap<>(worldList.size());
        for (World world : worldList) {
            mapToReturn.put(world, conditionalTranslator.worldToFormula(world));
        }
        return mapToReturn;
    }

    public Map<Integer, PConditional> getNfcMap() {
        return newNfcMap;
    }


}



