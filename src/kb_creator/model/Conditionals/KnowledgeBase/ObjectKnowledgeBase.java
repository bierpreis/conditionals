package kb_creator.model.Conditionals.KnowledgeBase;

import kb_creator.model.Conditionals.NewConditional;
import kb_creator.model.PropositionalLogic.AbstractFormula;
import kb_creator.model.PropositionalLogic.Tautology;
import kb_creator.model.PropositionalLogic.Worlds.AbstractWorld;
import kb_creator.model.Signature.AB;
import kb_creator.model.Signature.ABC;
import kb_creator.model.Signature.AbstractSignature;


import java.util.ArrayList;
import java.util.List;

public class ObjectKnowledgeBase extends AbstractKnowledgeBase {

    private List<NewConditional> conditionalList;


    public ObjectKnowledgeBase(AbstractSignature signature, int kbNumber) {
        conditionalList = new ArrayList<>();
        this.signature = signature;
        this.kbNumber = kbNumber;
    }

    public ObjectKnowledgeBase(String stringFromFile) {


        String[] splitString = stringFromFile.split(".*\nsignature\n");

        System.out.println("string: " + splitString[1]);
        if (splitString[1].matches("ab\n" +
                "\n" +
                "conditionals\n" +
                "1\n" +
                "\n")) {
            System.out.println("nice it matched");
            //todo wtf why this doenst work. try to build the matching stuff diffrent. sth with matcher?
            signature = new AB();
        } else throw new RuntimeException("No valid signature found in file");
        System.out.println("niece!!!");
        conditionalList = new ArrayList<>();
        stringFromFile.replace(".*conditionals\n\n", "");
        String[] conditionalStringArray = stringFromFile.split(", ");

        for (String candidateString : conditionalStringArray)
            conditionalList.add(nfcMap.get(Integer.parseInt(candidateString)));

    }


    public boolean isConsistent(NewConditional conditionalToTest) {
        //this test is written in goldszmit/pearl 1996 p 64 (tolerance)
        //siehe auch infofc s 4 dazu. auch s 9 dort.

        AbstractFormula concistecyOfKB = new Tautology();

        for (NewConditional conditionalFromList : conditionalList) {
            concistecyOfKB = concistecyOfKB.and(conditionalFromList.getAntecend().neg().or(conditionalFromList.getConsequence()));
        }


        for (AbstractWorld world : signature.getPossibleWorlds()) {
            if (conditionalToTest.getAntecend().evaluate(world) && conditionalToTest.getConsequence().evaluate(world) && concistecyOfKB.evaluate(world)) {
                return true;
            }
        }

        return false;
    }

    public int getKbNumber() {
        return kbNumber;
    }

    @Override
    public void add(AbstractKnowledgeBase knowledgeBaseToAdd) {
        conditionalList.addAll(knowledgeBaseToAdd.getConditionalList());
    }

    @Override
    public int getSize() {
        return conditionalList.size();
    }


    @Override
    public String toString() {
        return getConditionalList().toString();
    }


    public List<NewConditional> getConditionalList() {
        return conditionalList;
    }

    @Override
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

    @Override
    public String toShortFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("signature\n");
        sb.append(signature.toString());
        sb.append("\n\n");
        sb.append("conditionals\n");

        for (int i = 0; i < conditionalList.size(); i++) {
            sb.append(conditionalList.get(i).getNumber());
            if (i != conditionalList.size() - 1)
                sb.append(", ");
        }
        return sb.toString();
    }

    public void add(NewConditional conditionalToAdd) {
        conditionalList.add(conditionalToAdd);
    }

}
