package nfc.view.textArea;

import nfc.model.Conditional;
import nfc.model.ConditionalList;
import nfc.model.World;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
            String currentLine = "";

            for (Conditional currentConditional : currentEqList.getList()) {
                if (isNumberingActive)
                    currentLine = currentLine + currentConditional.getNumber() + ": " + currentConditional.toString() + createWhiteSpaceString(currentConditional.toString().length());
                else
                    currentLine = currentLine + currentConditional.toString() + createWhiteSpaceString(currentConditional.toString().length());
                numberOfConditionals++;

            }
            append(currentLine + "\n");
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
