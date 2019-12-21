package nfc_creator.model;


import kb_creator.model.logic.AbstractFormula;
import kb_creator.model.logic.PConditional;
import kb_creator.model.logic.signature.AbstractSignature;

import java.util.*;

public class NfcCreator {
    private final List<WorldsList> worldsList;
    private final List<ConditionalList> wCnfcEq;
    private final List<WConditional> wConditionalList;
    private final List<WConditional> wCnfc;
    private final List<WConditional> wNfc;

    private final List<PConditional> pNfc;
    private final List<PConditional> pCnfc;

    private final Map<Integer, PConditional> pNfcMap;


    public NfcCreator(AbstractSignature signature) {
        System.out.println("now creating WConditionals");

        worldsList = createWorlds(signature);

        ConditionalTranslator.init(signature);

        //this is basic conditional list in order from definition  2
        wConditionalList = createBasicConditionalList(worldsList);

        //this creates a list of conditional lists which simplifies numbering and eq setting
        wCnfcEq = createWCnfcEq(wConditionalList);

        //this is in order on def 5.1
        wCnfc = createWCnfc(wCnfcEq);

        //this is in order of definition 5.2
        wNfc = createWNfc(wCnfcEq);

        //
        setRealEquivalentList(wCnfcEq);

        setCounterConditionals(wNfc);


        System.out.println("now creating PConditionals");

        pNfc = translateConditionals(wNfc);

        pCnfc = createPCnfc();

        pNfcMap = createNfcMap(pNfc);

        setEquivalentListToPConditionals(wNfc, pNfcMap);

        System.out.println("finished creating conditionals");
    }

    private List<PConditional> createPCnfc() {
        List<PConditional> pCnfc = new ArrayList<>(wCnfc.size());

        //add the conditionals from nfc instead of translating this again
        //because translating would create NEW conditionals
        for (int i = 0; i < wCnfc.size(); i++)
            pCnfc.add(pNfc.get(i));

        return pCnfc;
    }


    private List<WorldsList> createWorlds(AbstractSignature signature) {
        WorldsList.setSignature(signature);
        int numberOfWorlds = signature.getPossibleWorlds().size();
        List<Integer> initWorldsList = new ArrayList<>();
        for (int i = numberOfWorlds - 1; i >= 0; i--) {
            initWorldsList.add(i);
        }

        List<WorldsList> worldsList = createSubSetList(initWorldsList);

        Collections.sort(worldsList);

        for (int i = 1; i <= worldsList.size(); i++)
            worldsList.get(i - 1).setNumber(i);


        return worldsList;
    }


    private List<WConditional> createWNfc(List<ConditionalList> cnfc) {
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


    private List<WConditional> createBasicConditionalList(List<WorldsList> worldsList) {
        List<WConditional> basicConditionalList = new ArrayList<>();

        for (WorldsList currentWorld : worldsList) {
            List<WConditional> currentConditionalList = new ArrayList<>();
            List<WorldsList> allSubSetsOfCurrentWorld = createSubSetList(currentWorld.getWorldsList());

            for (WorldsList currentSubWorld : allSubSetsOfCurrentWorld) {
                //only add real subsets not the set itself
                if (!currentSubWorld.equals(currentWorld))
                    currentConditionalList.add(new WConditional(currentSubWorld, currentWorld));
            }
            basicConditionalList.addAll(currentConditionalList);

        }
        Collections.sort(basicConditionalList);

        return basicConditionalList;
    }


    private List<ConditionalList> createWCnfcEq(final List<WConditional> basicConditionalList) {

        List<ConditionalList> cNfc = new ArrayList<>();

        Set<WConditional> alreadyAddedSet = new HashSet<>();

        //iterate basic conditionals
        for (WConditional firstConditional : basicConditionalList) {
            //only create new sublist if conditional was not added before as second conditional
            if (!alreadyAddedSet.contains(firstConditional)) {
                ConditionalList subList = new ConditionalList();
                subList.add(firstConditional);
                alreadyAddedSet.add(firstConditional);
                //iterate over base list
                for (WConditional possibleEquivalentConditional : basicConditionalList) {
                    //try to find equivalent conditionals
                    if (possibleEquivalentConditional.isEquivalent(firstConditional) && !alreadyAddedSet.contains(possibleEquivalentConditional)) {
                        //avoid adding the same base conditionals again
                        //if (!possibleEquivalentConditional.equals(firstConditional)) {
                        subList.add(possibleEquivalentConditional);
                        alreadyAddedSet.add(possibleEquivalentConditional);
                        //}
                    }
                }
                cNfc.add(subList);
            }
        }
        Collections.sort(cNfc);

        //set the numbers
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


    private void setRealEquivalentList(List<ConditionalList> cnfcEq) {
        for (ConditionalList conditionalList : cnfcEq) {

            //collect all equivalent conditionals into the first in the eq class
            for (int i = 0; i < conditionalList.getList().size(); i++)
                conditionalList.get(0).addEqConditional(conditionalList.get(i));


            //add eq list to all but the first in the conditional list
            for (int j = 1; j < conditionalList.getList().size(); j++)
                conditionalList.get(j).addEqList(conditionalList.get(0).getRealEqList());
        }
    }

    private List<WConditional> createWCnfc(List<ConditionalList> cnfcEq) {
        List<WConditional> cnfc = new ArrayList<>(cnfcEq.size());

        for (ConditionalList sublist : cnfcEq)
            cnfc.add(sublist.get(0));


        return cnfc;
    }

    //this adds the numbers of equivalent conditionals to every new nfc conditional
    //this list is needed to reduce the possible candidates when initialising candidate pairs
    private void setEquivalentListToPConditionals(List<WConditional> wNfc, Map<Integer, PConditional> wNfcMap) {
        for (WConditional conditional : wNfc) {
            List<PConditional> tempEqList = new ArrayList<>(conditional.getRealEqList().size());
            for (WConditional eqConditional : conditional.getRealEqList())
                tempEqList.add(this.pNfcMap.get(eqConditional.getNumber()));
            wNfcMap.get(conditional.getNumber()).setEqList(tempEqList);
        }
    }


    private List<WorldsList> createSubSetList(List<Integer> inputList) {
        List<WorldsList> subSetList = new ArrayList<>();
        for (Integer world : inputList) {
            for (ListIterator<WorldsList> setsIterator = subSetList.listIterator(); setsIterator.hasNext(); ) {
                WorldsList newWorld = new WorldsList();
                newWorld.addList(setsIterator.next().getWorldsList());
                newWorld.addInt(world);
                setsIterator.add(newWorld);
            }
            WorldsList otherWorld = new WorldsList();
            otherWorld.addInt(world);
            subSetList.add(otherWorld);
        }
        return subSetList;
    }


    private List<PConditional> translateConditionals(List<WConditional> wConditionalList) {
        List<PConditional> pConditionalList = new ArrayList<>(wConditionalList.size());

        for (WConditional wConditional : wConditionalList) {
            PConditional pConditional = ConditionalTranslator.transLate(wConditional);


            pConditional.setCounterConditional(ConditionalTranslator.transLate(wConditional.getActualCounterConditional()));
            pConditionalList.add(pConditional);

        }

        //this takes the basic counter conditional and replaces it with the actual counter conditional reference
        //this saves some memory space, but not very much.
        for (PConditional conditional : pConditionalList) {
            for (PConditional possibleCounterConditional : pConditionalList) {
                if (conditional.getCounterConditional().equals(possibleCounterConditional)) {
                    conditional.setCounterConditional(possibleCounterConditional);
                    break;
                }
            }
        }

        //comment out the following 2 lines and you can see if the counter conditionals are set correct
        //for (WConditional conditional : pConditionals)
        //   System.out.println("org: " + conditional.getNumber() + " counter: " + conditional.findCounterConditional().getNumber());

        return pConditionalList;
    }


    private void setCounterConditionals(List<WConditional> nfc) {
        System.out.println("creating counter conditionals");

        //this takes very long. but is maybe the only way to set the counter conditional numbers?
        long startTime = System.currentTimeMillis();
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
                throw new RuntimeException("Double conditional detected! (" + conditional.getNumber() + ")");
            }
            conditionalMap.put(conditional.getNumber(), conditional);
        }

        //make it unmodifiable so no accidentally changed to this map can happen
        return Collections.unmodifiableMap(conditionalMap);
    }

    public Map<WorldsList, AbstractFormula> createWorldsFormulasMap() {
        Map<WorldsList, AbstractFormula> mapToReturn = new HashMap<>(worldsList.size());
        for (WorldsList world : worldsList) {
            mapToReturn.put(world, ConditionalTranslator.worldToFormula(world));
        }
        return mapToReturn;
    }


    //getters only for viewer
    public List<WorldsList> getWorldsList() {
        return worldsList;
    }

    public List<ConditionalList> getwCnfcEq() {
        return wCnfcEq;
    }

    public List<WConditional> getWConditionalList() {
        return wConditionalList;
    }

    public List<WConditional> getwNfc() {
        return wNfc;
    }

    public List<WConditional> getwCnfc() {
        return wCnfc;
    }


    //getters for creator

    public Map<Integer, PConditional> getNfcMap() {
        return pNfcMap;
    }

    public List<PConditional> getpNfc() {
        return pNfc;
    }

    public List<PConditional> getpCnfc() {
        return pCnfc;
    }


}



