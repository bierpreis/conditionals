package kb_creator.model.logic;

import kb_creator.model.logic.signature.AbstractSignature;
import kb_creator.model.logic.signature.worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class KnowledgeBase {
    private static Map<Integer, PConditional> nfcMap;
    private static AbstractSignature signature;

    private int number;
    private final List<PConditional> conditionalList;

    //making these patterns static saves A LOT of memory

    private static final Pattern NEW_LINE_PATTERN = Pattern.compile("\n");
    private static final Pattern KB_PATTERN = Pattern.compile("KB");

    private static final Pattern CURLY_BRACKET_START = Pattern.compile("\\{");
    private static final Pattern CURLY_BRACKET_STOP = Pattern.compile("}");

    private static final Pattern COMMA_PATTERN = Pattern.compile(",");

    //this is used for first iterations
    public KnowledgeBase(int number, PConditional conditional) {
        this.number = number;
        this.conditionalList = new ArrayList<>(1);
        this.conditionalList.add(conditional);
    }

    //this is used for all but first iteration
    public KnowledgeBase(int number, KnowledgeBase knowledgeBase, PConditional conditionalToAdd) {
        this.number = number;

        this.conditionalList = new ArrayList<>(knowledgeBase.getConditionalList().size() + 1);
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
        this.number = Integer.parseInt(splitString3[0]);

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
                    if (!conditional.tolerates(world)) {
                        toleratesAll = false;
                        break;
                    }
                }
                if (toleratesAll)
                    return true;
            }
        }
        return false;

    }


    @Override
    public String toString() {
        return conditionalList.toString();
    }

    //todo: anpassen! signature muss raus in kb writer.
    //this creates infOcf file strings
    public String toFileString() {

        StringBuilder sb = new StringBuilder();

        //this was used for writing only 1 kb per file.
       // sb.append("signature\n");
        //sb.append(signature.toString().toLowerCase());
        //sb.append("\n\n");


        sb.append("conditionals\n");
        sb.append(this.number);
        sb.append("{\n");

        for (int i = 0; i < conditionalList.size(); i++) {
            sb.append(conditionalList.get(i));
            if (i != conditionalList.size() - 1)
                sb.append(",\n");
        }

        sb.append("\n}");
        return sb.toString();
    }

    //this creates shorter file strings. used by pair writer.
    public String toShortFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.number);
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

    private List<PConditional> getConditionalList() {
        return conditionalList;
    }

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return conditionalList.size();
    }


    //setters

    public static void setNfcMap(Map<Integer, PConditional> nfcMapToAdd) {
        nfcMap = nfcMapToAdd;
    }

    public static void setSignature(AbstractSignature signatureToSet) {
        signature = signatureToSet;
    }

    public void setNumber(int number) {
        if (this.number != 0)//exception to make sure numbers are set only once
            throw new RuntimeException("Cant set kb number two times!");
        this.number = number;
    }

}
