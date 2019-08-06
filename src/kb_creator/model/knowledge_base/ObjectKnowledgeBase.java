package kb_creator.model.knowledge_base;

import kb_creator.model.propositional_logic.Conjunction;
import kb_creator.model.propositional_logic.NewConditional;
import kb_creator.model.propositional_logic.AbstractFormula;
import kb_creator.model.propositional_logic.Tautology;
import kb_creator.model.propositional_logic.worlds.AbstractWorld;
import kb_creator.model.propositional_logic.signature.AB;
import kb_creator.model.propositional_logic.signature.ABC;
import kb_creator.model.propositional_logic.signature.AbstractSignature;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ObjectKnowledgeBase extends AbstractKnowledgeBase {

    private List<NewConditional> conditionalList;

    private final Pattern AB_PATTERN = Pattern.compile("^a,b.*");
    private final Pattern ABC_PATTERN = Pattern.compile("^a,b,c.*");


    public ObjectKnowledgeBase(AbstractSignature signature, int kbNumber) {
        this.conditionalList = new ArrayList<>();
        this.signature = signature;
        this.kbNumber = kbNumber;


    }

    //this constructor takes almost no time
    public ObjectKnowledgeBase(String stringFromFile) {
        stringFromFile = stringFromFile.replaceAll("\n", "");
        String[] splitString1 = stringFromFile.split("signature");

        if (ABC_PATTERN.matcher(splitString1[1]).matches())
            signature = new ABC();
        else if (AB_PATTERN.matcher(splitString1[1]).matches())
            signature = new AB();
        else throw new RuntimeException("No valid signature found in file: " + splitString1[1]);


        String[] splitString2 = stringFromFile.split("conditionals");
        String[] splitString3 = splitString2[1].split("\\{");
        this.kbNumber = Integer.parseInt(splitString3[0]);
        String[] conditionalStringArray = splitString3[1].replaceAll("\\}", "").split(", ");

        conditionalList = new ArrayList<>(conditionalStringArray.length);

        for (String candidateString : conditionalStringArray)
            conditionalList.add(nfcMap.get(Integer.parseInt(candidateString)));
    }

    //todo: really think about this again. very important!!
    //this takes about 4 micosecs
    public boolean isConsistent(NewConditional conditionalToTest) {
        //long start = System.nanoTime();
        //this test is written in goldszmit/pearl 1996 p 64 (tolerance)
        //siehe auch infofc s 4 dazu. auch s 9 dort.

        AbstractFormula consistencyOfKB = null;


        for (NewConditional conditionalFromList : conditionalList) {
            if (consistencyOfKB == null)
                consistencyOfKB = new Conjunction(conditionalFromList.getAntecedent().neg().or(conditionalFromList.getConsequence()));
            else
                consistencyOfKB = consistencyOfKB.and(conditionalFromList.getAntecedent().neg().or(conditionalFromList.getConsequence()));
        }

        for (AbstractWorld world : signature.getPossibleWorlds()) {
            //System.out.println(conditionalToTest);
            if (conditionalToTest.getAntecedent().evaluate(world) && conditionalToTest.getConsequence().evaluate(world) && consistencyOfKB.evaluate(world)) {
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