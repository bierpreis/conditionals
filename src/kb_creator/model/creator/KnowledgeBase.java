package kb_creator.model.creator;

import kb_creator.model.propositional_logic.PConditional;
import kb_creator.model.propositional_logic.signature.AbstractSignature;
import kb_creator.model.propositional_logic.worlds.AbstractWorld;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class KnowledgeBase {


    private static Map<Integer, PConditional> nfcMap;
    private static AbstractSignature signature;
    private int kbNumber;



    //setters

    public static void setNfcMap(Map<Integer, PConditional> nfcMapToAdd) {
        nfcMap = nfcMapToAdd;
    }

    public static void setSignature(AbstractSignature signatureToSet) {
        signature = signatureToSet;
    }

    public void setKbNumber(int kbNumber) {

        //exception to make sure numbers are set only once
        if (this.kbNumber != 0)
            throw new RuntimeException("Cant set kb number two times!");

        this.kbNumber = kbNumber;
    }

    //getters

    public int getKbNumber() {
        return kbNumber;
    }




    private final List<PConditional> conditionalList;

    //making theese static saves A LOT of memory
    private static final Pattern AB_PATTERN = Pattern.compile("^a,b.*");
    private static final Pattern ABC_PATTERN = Pattern.compile("^a,b,c.*");

    private static final Pattern NEW_LINE_PATTERN = Pattern.compile("\n");
    private static final Pattern SIGNATURE_PATTERN = Pattern.compile("signature");
    private static final Pattern KB_PATTERN = Pattern.compile("KB");

    private static final Pattern CURLY_BRACKET_START = Pattern.compile("\\{");
    private static final Pattern CURLY_BRACKET_STOP = Pattern.compile("}");

    private static final Pattern COMMA_PATTERN = Pattern.compile(",");

    //private AbstractFormula consistencyOfKB;

    //this constructor is only used for initializing 1 element kbs
    public KnowledgeBase() {
        this.conditionalList = new ArrayList<>(1);
    }

    //this is used by parallel creator
    public KnowledgeBase(KnowledgeBase knowledgeBase, PConditional conditionalToAdd) {
        this.conditionalList = new ArrayList<>(knowledgeBase.getConditionalList().size() + 1);
        this.conditionalList.addAll(knowledgeBase.getConditionalList());
        this.conditionalList.add(conditionalToAdd);
    }

    //this constructor is used for all the other iterations
    //legacy
    public KnowledgeBase(int kbNumber, KnowledgeBase knowledgeBase, PConditional conditionalToAdd) {
        this.conditionalList = new ArrayList<>(knowledgeBase.getConditionalList().size() + 1);
        this.kbNumber = kbNumber;

        conditionalList.addAll(knowledgeBase.getConditionalList());
        conditionalList.add(conditionalToAdd);
    }

    //this constructor takes almost no time
    public KnowledgeBase(String stringFromFile) {
        stringFromFile = NEW_LINE_PATTERN.matcher(stringFromFile).replaceAll("");

        /*        //this would be needed to read the infOcf kbs

        String[] splitString1 = SIGNATURE_PATTERN.split(stringFromFile);


        if (ABC_PATTERN.matcher(splitString1[1]).matches())
            signature = new ABC();
        else if (AB_PATTERN.matcher(splitString1[1]).matches())
            signature = new AB();
        else throw new RuntimeException("No valid signature found in file: " + splitString1[1]);*/


        String[] splitString2 = KB_PATTERN.split(stringFromFile);
        String[] splitString3 = CURLY_BRACKET_START.split(splitString2[1]);
        this.kbNumber = Integer.parseInt(splitString3[0]);
        String[] conditionalStringArray = COMMA_PATTERN.split(CURLY_BRACKET_STOP.matcher(splitString3[1]).replaceAll(""));

        conditionalList = new ArrayList<>(conditionalStringArray.length);

        for (String candidateString : conditionalStringArray)
            conditionalList.add(nfcMap.get(Integer.parseInt(candidateString)));


    }

    public boolean isConsistentWith(PConditional conditionalToTest) {

        //hauptquelle:
        //this test is written in goldszmit/pearl 1996 p 64 (tolerance)
        //
        // nicht so wichtig dazu, vlt comment streichen:
        // siehe auch infofc s 4 dazu. auch s 9 dort.

        for (AbstractWorld world : signature.getPossibleWorlds()) {
            if (conditionalToTest.getAntecedent().evaluate(world) && conditionalToTest.getConsequence().evaluate(world)) {
                boolean toleratesAll = true;
                for (PConditional conditional : conditionalList) {
                    if (!conditional.tolerates(world))
                        toleratesAll = false;
                }
                if (toleratesAll)
                    return true;
            }
        }
        return false;

    }


    public void add(PConditional conditionalToAdd) {
        conditionalList.add(conditionalToAdd);
    }


    @Override
    public String toString() {
        return conditionalList.toString();
    }


    //this creates infOcf file strings
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

    //this creates shorter file strings
    public String toShortFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.kbNumber);
        sb.append("{");

        for (int i = 0; i < conditionalList.size(); i++) {
            sb.append(conditionalList.get(i).getNumber());
            if (i != conditionalList.size() - 1)
                sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }

    //getters

    public List<PConditional> getConditionalList() {
        return conditionalList;
    }


    public int getSize() {
        return conditionalList.size();
    }

}
