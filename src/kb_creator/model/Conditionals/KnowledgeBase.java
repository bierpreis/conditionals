package kb_creator.model.Conditionals;

import gherkin.lexer.Kn;
import kb_creator.model.PropositionalLogic.AbstractFormula;
import kb_creator.model.PropositionalLogic.Tautology;
import kb_creator.model.PropositionalLogic.Worlds.AbstractWorld;
import kb_creator.model.Signature.AB;
import kb_creator.model.Signature.ABC;
import kb_creator.model.Signature.AbstractSignature;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KnowledgeBase {
    private AbstractSignature signature;
    private List<Integer> conditionalNumbersList;
    private int kbNumber;

    private static Map<Integer, NewConditional> nfcMap;

    public KnowledgeBase(AbstractSignature signature, int kbNumber) {
        conditionalNumbersList = new ArrayList<>();
        this.signature = signature;
        this.kbNumber = kbNumber;
    }

    public KnowledgeBase(String stringFromFile) {
        //todo: test
        System.out.println("string in kb: " + stringFromFile);
        stringFromFile.replace("signature\n", "");

        if (stringFromFile.matches("^a,b"))
            signature = new AB();
        else if (stringFromFile.matches("^a,b,c"))
            signature = new ABC();
        else throw new RuntimeException("No valid signature found in file");

        conditionalNumbersList = new ArrayList<>();
        stringFromFile.replace(".*conditionals\n\n", "");
        String[] conditionalStringArray = stringFromFile.split(", ");

        for (String candidateString : conditionalStringArray)
            conditionalNumbersList.add(Integer.parseInt(candidateString));

    }


    //todo: this
    public boolean isConsistent(NewConditional conditionalToTest) {
        //this test is written in goldszmit/pearl 1996 p 64 (tolerance)
        //siehe auch infofc s 4 dazu. auch s 9 dort.

        List<NewConditional> conditionalList = new ArrayList<>();
        for (Integer conditionalNumber : conditionalNumbersList) {
            conditionalList.add(nfcMap.get(conditionalNumber));
        }


        AbstractFormula concistecyOfKB = new Tautology();

        for (NewConditional conditionalFromList : conditionalList) {
            concistecyOfKB = concistecyOfKB.and(conditionalFromList.getAntecend().neg().or(conditionalFromList.getConsequence()));
        }

        //here sth like:
        for (AbstractWorld world : signature.getPossibleWorlds()) {
            if (conditionalToTest.getAntecend().evaluate(world) && conditionalToTest.getConsequence().evaluate(world) && concistecyOfKB.evaluate(world)) {

//                System.out.println("consistent: " + world.toString());
//                System.out.println(conditionalToTest);
//                System.out.println(concistecyOfKB);
//                System.out.println();

                return true;

            }

        }

//        System.out.println("inconsistent: ");
//        System.out.println(conditionalToTest);
//        System.out.println(concistecyOfKB);
//        System.out.println();

        return false;
    }

    public int getKbNumber() {
        return kbNumber;
    }

    public void add(Integer conditionalNumberToAdd) {
        conditionalNumbersList.add(conditionalNumberToAdd);
    }

    public static void setNfcMap(Map<Integer, NewConditional> nfcMapToAdd) {
        nfcMap = nfcMapToAdd;
    }

    public void add(KnowledgeBase knowledgeBaseToAdd) {
        for (Integer conditionalNumber : knowledgeBaseToAdd.getConditionalNumbersList()) {
            conditionalNumbersList.add(conditionalNumber);
        }
    }

    public List<Integer> getConditionalNumbersList() {
        return conditionalNumbersList;
    }

    public int getSize() {
        return conditionalNumbersList.size();
    }


    @Override
    public String toString() {
        return getConditionalList().toString();
    }


    public List<NewConditional> getConditionalList() {
        List<NewConditional> conditionalList = new ArrayList<>();
        for (Integer conditionalNumber : conditionalNumbersList) {
            conditionalList.add(nfcMap.get(conditionalNumber));
        }
        return conditionalList;
    }


    public String toFileString() {

        StringBuilder sb = new StringBuilder();
        sb.append("signature\n");
        sb.append(signature.toString().toLowerCase());
        sb.append("\n");
        sb.append("conditionals\n\n");
        sb.append(this.kbNumber);
        sb.append("{\n");

        List<NewConditional> conditionalList = getConditionalList();
        for (int i = 0; i < conditionalList.size(); i++) {
            sb.append(conditionalList.get(i));
            if (i != conditionalList.size() - 1)
                sb.append(",\n");

        }
        sb.append("\n}");
        return sb.toString();

    }

    public String toShortFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("signature");
        sb.append(signature.toString());
        sb.append("\n\n");
        sb.append("conditionals: \n\n");
        for (Integer conditionalInt : conditionalNumbersList) {
            sb.append(conditionalInt);
            sb.append(", ");
        }


        return sb.toString();
    }

}
