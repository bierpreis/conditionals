package nfc_creator.view.textArea;

import kb_creator.model.logic.AbstractFormula;
import nfc_creator.model.WConditional;
import nfc_creator.model.ConditionalList;
import nfc_creator.model.WorldsList;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CondTextField extends JTextArea {
    private final String numberOfWorlds = "worlds: ";
    private final String numberOfConditionals = "conditionals: ";
    private final String numberOfEquivalenceClasses = "equivalence classes: ";

    private String description;

    private static boolean isNumberingActive = false;

    public CondTextField() {
        super(40, 90);

        setFont(new Font("monospaced", Font.PLAIN, 12));
    }


    public void printWorlds(List<WorldsList> worldsList) {
        setText("");

        for (WorldsList world : worldsList) {
            if (isNumberingActive)
                append(String.format("%04d", world.getNumber()) + ": ");
            append(world.toString() + "\n");

        }

        description = numberOfWorlds + worldsList.size();
    }

    void printWorldsAndFormulas(List<WorldsList> worldsList, Map<WorldsList, AbstractFormula> translationMap) {
        for (WorldsList world : worldsList) {
            if (isNumberingActive)
                append(String.format("%04d",world.getNumber()) + ": ");
            append(world.toString() + " -> " + translationMap.get(world).toString() + "\n");

        }

        description = "number of worlds and formulas: " + translationMap.size();
    }

    public void printConditionals(List<WConditional> conditionalList) {

        setText("");
        String conditionalString;
        int conditionalCounter = 0;
        for (WConditional conditional : conditionalList) {

            conditionalString = conditional.toString() + "\n";


            if (isNumberingActive)
                conditionalString = String.format("%04d",conditional.getNumber()) + ": " + conditionalString;
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
            for (WConditional currentConditional : currentEqList.getList()) {
                if (isNumberingActive)
                    sb.append(String.format("%04d",currentConditional.getNumber()) + ": " + currentConditional.toString() + createWhiteSpaceString(currentConditional.toString().length()));
                else
                    sb.append(currentConditional.toString() + createWhiteSpaceString(currentConditional.toString().length()));
                numberOfConditionals++;

            }
            sb.append("\n");
            append(sb.toString());
        }
        description = this.numberOfEquivalenceClasses + eqClassList.size() + System.lineSeparator() + "   " + this.numberOfConditionals + numberOfConditionals; //line separator doesn't work?!
    }

    private String createWhiteSpaceString(int conditionalLength) {
        int numberOfWhiteSpaces = WConditional.getLongestConditional() - conditionalLength;
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

    public void printConditionalsWithCounters(List<WConditional> conditionals) {
        setText("");
        {
            for (WConditional conditional : conditionals) {
                if (isNumberingActive)
                    append(String.format("%04d",conditional.getNumber()) + ": " + conditional.toString() + "  counter: " + String.format("%04d",conditional.getActualCounterConditional().getNumber()) + ": " + conditional.getActualCounterConditional().toString() + "\n");
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
