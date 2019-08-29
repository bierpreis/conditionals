package kb_creator.model.knowledge_base;

import kb_creator.model.propositional_logic.NewConditional;
import kb_creator.model.propositional_logic.AbstractFormula;
import kb_creator.model.propositional_logic.worlds.AbstractWorld;
import kb_creator.model.propositional_logic.signature.AB;
import kb_creator.model.propositional_logic.signature.ABC;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ObjectKnowledgeBase extends AbstractKnowledgeBase {

    private List<NewConditional> conditionalList;

    //making theese static saves A LOT of memory
    private static final Pattern AB_PATTERN = Pattern.compile("^a,b.*");
    private static final Pattern ABC_PATTERN = Pattern.compile("^a,b,c.*");

    private static final Pattern NEW_LINE_PATTERN = Pattern.compile("\n");
    private static final Pattern SIGNATURE_PATTERN = Pattern.compile("signature");
    private static final Pattern CONDITONALS_PATTERN = Pattern.compile("conditionals");

    private static final Pattern CURLY_BRACKET_START = Pattern.compile("\\{");
    private static final Pattern CURLY_BRACKET_STOP = Pattern.compile("\\}");

    private static final Pattern COMMA_SPACE_PATTERN = Pattern.compile(", ");

    private AbstractFormula consistencyOfKB;

    //this constructor is only used for initializing 1 element kbs
    public ObjectKnowledgeBase(int kbNumber) {
        this.conditionalList = new ArrayList<>(1);
        this.kbNumber = kbNumber;
    }

    //this constructor is used for all the other iterations
    public ObjectKnowledgeBase(int kbNumber, AbstractKnowledgeBase knowledgeBase, NewConditional conditionalToAdd) {
        this.conditionalList = new ArrayList<>(knowledgeBase.getConditionalList().size() + 1);
        this.kbNumber = kbNumber;

        conditionalList.addAll(knowledgeBase.getConditionalList());
        conditionalList.add(conditionalToAdd);
    }
    
    //this constructor takes almost no time
    public ObjectKnowledgeBase(String stringFromFile) {
        stringFromFile = NEW_LINE_PATTERN.matcher(stringFromFile).replaceAll("");
        String[] splitString1 = SIGNATURE_PATTERN.split(stringFromFile);

        //todo: not sure if remove
        if (ABC_PATTERN.matcher(splitString1[1]).matches())
            signature = new ABC();
        else if (AB_PATTERN.matcher(splitString1[1]).matches())
            signature = new AB();
        else throw new RuntimeException("No valid signature found in file: " + splitString1[1]);


        String[] splitString2 = CONDITONALS_PATTERN.split(stringFromFile);
        String[] splitString3 = CURLY_BRACKET_START.split(splitString2[1]);
        this.kbNumber = Integer.parseInt(splitString3[0]);
        String[] conditionalStringArray = COMMA_SPACE_PATTERN.split(CURLY_BRACKET_STOP.matcher(splitString3[1]).replaceAll(""));

        conditionalList = new ArrayList<>(conditionalStringArray.length);

        for (String candidateString : conditionalStringArray)
            conditionalList.add(nfcMap.get(Integer.parseInt(candidateString)));


    }

    public boolean isConsistent(NewConditional conditionalToTest) {
        //hauptquelle:
        //this test is written in goldszmit/pearl 1996 p 64 (tolerance)
        //
        // nicht so wichtig dazu, vlt comment streichen:
        // siehe auch infofc s 4 dazu. auch s 9 dort.

        //only create this formula once for performance reasons
        if (consistencyOfKB == null)
            consistencyOfKB = createConsistencyFormula();


        //long start = System.nanoTime();

        //this takes about 4 micosecs
        for (AbstractWorld world : signature.getPossibleWorlds()) {
            //System.out.println(conditionalToTest);
            if (conditionalToTest.getAntecedent().evaluate(world) && conditionalToTest.getConsequence().evaluate(world) && consistencyOfKB.evaluate(world)) {
                return true;
            }
        }
        //System.out.println("time: " + (System.nanoTime() - start) / 1000);
        return false;

    }

    //create consistency formula to be reused for every consistency test
    //this increases overall speed about 20%
    private AbstractFormula createConsistencyFormula() {

        for (NewConditional conditionalFromList : conditionalList) {
            if (consistencyOfKB == null)
                //this is actually the implication
                consistencyOfKB = conditionalFromList.getAntecedent().neg().or(conditionalFromList.getConsequence());
            else
                consistencyOfKB = consistencyOfKB.and(conditionalFromList.getAntecedent().neg().or(conditionalFromList.getConsequence()));
        }
        return consistencyOfKB;
    }


    @Override
    public void add(NewConditional conditionalToAdd) {
        conditionalList.add(conditionalToAdd);
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

    //todo: remove signature from here?
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


}
