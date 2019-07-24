package kb_creator.model.conditionals.knowledge_base;

import kb_creator.model.conditionals.NewConditional;
import kb_creator.model.propositional_logic.AbstractFormula;
import kb_creator.model.propositional_logic.Tautology;
import kb_creator.model.propositional_logic.worlds.AbstractWorld;
import kb_creator.model.propositional_logic.signature.AB;
import kb_creator.model.propositional_logic.signature.ABC;
import kb_creator.model.propositional_logic.signature.AbstractSignature;


import java.util.ArrayList;
import java.util.List;

public class ObjectKnowledgeBase extends AbstractKnowledgeBase {

    private List<NewConditional> conditionalList;


    public ObjectKnowledgeBase(AbstractSignature signature, int kbNumber) {
        this.conditionalList = new ArrayList<>();
        this.signature = signature;
        this.kbNumber = kbNumber;
    }

    public ObjectKnowledgeBase(String stringFromFile) {
        stringFromFile = stringFromFile.replaceAll("\n", "");
        String[] splitString1 = stringFromFile.split("signature");

        //todo: maybe shorten this like a,b,c[.]*
        if (splitString1[1].matches("a,b,c[.]*"))
            signature = new ABC();
        else if (splitString1[1].matches("a,b[.]*")) {
            signature = new AB();
        } else throw new RuntimeException("No valid signature found in file");


        String[] splitString2 = stringFromFile.split("conditionals");
        String[] splitString3 = splitString2[1].split("\\{");
        this.kbNumber = Integer.parseInt(splitString3[0]);
        String[] conditionalStringArray = splitString3[1].replaceAll("\\}", "").split(", ");

        conditionalList = new ArrayList<>(conditionalStringArray.length);

        for (String candidateString : conditionalStringArray)
            conditionalList.add(nfcMap.get(Integer.parseInt(candidateString)));

    }

    //todo: really think about this again. very important!!
    public boolean isConsistent(NewConditional conditionalToTest) {
        //this test is written in goldszmit/pearl 1996 p 64 (tolerance)
        //siehe auch infofc s 4 dazu. auch s 9 dort.

        AbstractFormula concistecyOfKB = new Tautology();

        for (NewConditional conditionalFromList : conditionalList) {
            concistecyOfKB = concistecyOfKB.and(conditionalFromList.getAntecend().neg().or(conditionalFromList.getConsequence()));
        }


        for (AbstractWorld world : signature.getPossibleWorlds()) {
            //System.out.println(conditionalToTest);
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
        return conditionalList.toString();
    }


    public List<NewConditional> getConditionalList() {
        return conditionalList;
    }


    @Override
    public String toFileString() {

        StringBuilder sb = new StringBuilder();
        sb.append("signature\n");
        sb.append(signature.toString().toLowerCase());
        sb.append("\n\n");
        sb.append("conditionals\n");
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
        sb.append(this.kbNumber);
        sb.append("{\n");

        for (int i = 0; i < conditionalList.size(); i++) {
            sb.append(conditionalList.get(i).getNumber());
            if (i != conditionalList.size() - 1)
                sb.append(", ");
        }
        sb.append("\n}");
        return sb.toString();
    }

    public void add(NewConditional conditionalToAdd) {
        conditionalList.add(conditionalToAdd);
    }

}
