package kb_creator.model.logic;

import kb_creator.gui.left_panel.optionsPanel.warnings.AbstractWarningDialog;
import kb_creator.model.logic.signature.AbstractSignature;
import kb_creator.model.logic.signature.worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class KnowledgeBase {
    private static Map<Integer, PConditional> nfcMap;
    private static AbstractSignature signature;


    private static String namePrefixString = "kb";

    private int number;
    private final List<PConditional> conditionalList;

    //making these patterns static saves A LOT of memory

    private static final Pattern NEW_LINE_PATTERN = Pattern.compile("\n");
    private static final Pattern KB_PATTERN = Pattern.compile("KB");

    private static final Pattern CURLY_BRACKET_START = Pattern.compile("\\{");
    private static final Pattern CURLY_BRACKET_STOP = Pattern.compile("}");

    private static final Pattern COMMA_PATTERN = Pattern.compile(",");

    //todo: rethink if this all should be unmodifiable. makes it slower.

    //this is used for first iterations
    public KnowledgeBase(int number, PConditional conditional) {
        this.number = number;
        List<PConditional> newList = new ArrayList<>(1);
        newList.add(conditional);
        conditionalList = Collections.unmodifiableList(newList);
    }

    //this is used for all but first iteration
    public KnowledgeBase(int number, KnowledgeBase knowledgeBase, PConditional conditionalToAdd) {
        this.number = number;

        List<PConditional> newList = new ArrayList<>(knowledgeBase.getConditionalList().size() + 1);

        newList.addAll(knowledgeBase.getConditionalList());
        newList.add(conditionalToAdd);

        conditionalList = Collections.unmodifiableList(newList);
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

        List<PConditional> newList = new ArrayList<>(conditionalStringArray.length);

        for (String candidateString : conditionalStringArray)
            newList.add(nfcMap.get(Integer.parseInt(candidateString)));

        conditionalList = Collections.unmodifiableList(newList);


    }

    //todo
    public boolean tolerates(PConditional conditionalToTest) {

        //hauptquelle:
        //this test is written in goldszmit/pearl 1996 p 64 (tolerance)
        //
        // nicht so wichtig dazu, vlt comment streichen:
        // siehe auch infofc s 4 dazu. auch s 9 dort.



/*        if (this.conditionalList.get(0).getNumber() == 1 && conditionalToTest.getNumber() == 5)
            System.out.println("lel");*/


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

    public boolean isConsistentWith2(PConditional conditionalToTest) {
        for (AbstractWorld world : signature.getPossibleWorlds()) {
            if (conditionalToTest.getAntecedent().evaluate(world) && conditionalToTest.getConsequence().evaluate(world)) {
                AbstractFormula tolerannceFormula = new Tautology();
                for (PConditional conditional : conditionalList) {
                    tolerannceFormula = tolerannceFormula.and(conditional.getAntecedent().neg().or(conditional.getConsequence()));
                }
                if (tolerannceFormula.evaluate(world))
                    return true;
            }

        }
        return false;
    }


    @Override
    public String toString() {
        return conditionalList.toString();
    }


    //this creates infOcf file strings
    public String toStandardFileString() {

        StringBuilder sb = new StringBuilder();

        //this was used for writing only 1 kb per file.
        // sb.append("signature\n");
        //sb.append(signature.toString().toLowerCase());
        //sb.append("\n\n");

        sb.append(namePrefixString);
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

    public String toNumbersFileString() {

        StringBuilder sb = new StringBuilder();

        //this was used for writing only 1 kb per file.
        // sb.append("signature\n");
        //sb.append(signature.toString().toLowerCase());
        //sb.append("\n\n");

        sb.append(namePrefixString);
        sb.append(this.number);
        sb.append("{\n");

        for (int i = 0; i < conditionalList.size(); i++) {
            sb.append(conditionalList.get(i).getNumber());
            if (i != conditionalList.size() - 1)
                sb.append(", ");
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

    public AbstractSignature getSignature() {
        return signature;
    }

    public static void setKbNamePrefix(String kbNamePrefix) {
        namePrefixString = kbNamePrefix;
    }

    public String getNamePrefix() {
        return namePrefixString;
    }

}
