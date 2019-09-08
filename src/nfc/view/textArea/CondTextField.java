package nfc.view.textArea;

import kb_creator.model.propositional_logic.AbstractFormula;
import nfc.model.Conditional;
import nfc.model.ConditionalList;
import nfc.model.World;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CondTextField extends JTextArea {
    private final String numberOfWorlds = "worlds: ";
    private final String numberOfConditionals = "conditionals: ";
    private final String numberOfEquivalenceClasses = "Equivalence Classes: ";

    private String description;

    private static boolean isNumberingActive = false;

    public CondTextField() {
        super(40, 70);

        setFont(new Font("monospaced", Font.PLAIN, 12));
    }


    public void printWorlds(List<World> worldsList) {
        setText("");
        int numberCounter = 0;
        for (int i = 0; i < worldsList.size(); i++) {
            append(getLineNumber(i) + worldsList.get(i).toString() + "\n");
            numberCounter++;

        }
        description = numberOfWorlds + numberCounter;
    }

    void printWorldsAndFormulas(Map<World, AbstractFormula> translationMap) {
        for (Map.Entry<World, AbstractFormula> entry : translationMap.entrySet()) {
            append("\n" + entry.getKey().toString() + " ->" + entry.getValue().toString());
        }
        //todo: ordering and description
        description = "number of worlds and formulas: " + translationMap.size();
    }

    public void printConditionals(List<Conditional> conditionalList) {

        setText("");
        String conditionalString;
        int conditionalCounter = 0;
        for (Conditional conditional : conditionalList) {

            conditionalString = conditional.toString() + "\n";


            if (isNumberingActive)
                conditionalString = conditional.getNumber() + ": " + conditionalString;
            conditionalCounter++;


            append(conditionalString);
        }
        description = numberOfConditionals + conditionalCounter;


    }

    public void printCnfcEq(List<ConditionalList> eqClassList) {

        int numberOfConditionals = 0;
        setText("");

        for (ConditionalList currentEqList : eqClassList) {
            StringBuilder sb = new StringBuilder();
            for (Conditional currentConditional : currentEqList.getList()) {
                if (isNumberingActive)
                    sb.append(currentConditional.getNumber() + ": " + currentConditional.toString() + createWhiteSpaceString(currentConditional.toString().length()));
                else
                    sb.append(currentConditional.toString() + createWhiteSpaceString(currentConditional.toString().length()));
                numberOfConditionals++;

            }
            sb.append("\n");
            append(sb.toString());
        }
        description = this.numberOfEquivalenceClasses + eqClassList.size() + System.lineSeparator() + "   " + this.numberOfConditionals + numberOfConditionals; //line seperator doesnt work?!
    }

    private String createWhiteSpaceString(int conditionalLength) {
        int numberOfWhiteSpaces = Conditional.getLongestConditional() - conditionalLength;
        String whiteSpaceString = "";
        for (int i = 0; i < numberOfWhiteSpaces; i++) {
            whiteSpaceString = whiteSpaceString + " ";
        }
        return whiteSpaceString;
    }


    private String getLineNumber(int i) {
        String lineNumber;
        i = i + 1;
        if (i < 10)
            lineNumber = "0" + i;
        else lineNumber = Integer.toString(i);
        return lineNumber + "   ";
    }

    public void printConditionalsWithCounters(List<Conditional> conditionals) {
        setText("");
        {
            for (Conditional conditional : conditionals) {
                if (isNumberingActive)
                    append(conditional.getNumber() + ": " + conditional.toString() + "  counter: " + conditional.getActualCounterConditional().getNumber() + ": " + conditional.getActualCounterConditional().toString() + "\n");
                else
                    append(conditional.toString() + "  counter: " + conditional.getActualCounterConditional().toString() + "\n");
            }
        }
        {

        }
    }

    public String getContentAsString() {
        return getText();
    }

    public String getDescription() {
        return description;
    }

    public static void setNumberingActive(boolean numberingActive) {
        isNumberingActive = numberingActive;
    }
}
