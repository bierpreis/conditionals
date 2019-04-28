package nfc.view.textArea;

import nfc.model.Conditional;
import nfc.model.ConditionalList;
import nfc.model.World;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CondTextField extends JTextArea {
    private final String numberOfWorlds = "Worlds: ";
    private final String numberOfConditionals = "Conditionals: ";
    private final String numberOfEquivalenceClasses = "Equivalence Classes: ";

    private String description;

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

    public void printConditionals(List<Conditional> conditionalList, ViewOptions options) {

        Conditional.setSpaceDot(options.showDots());
        setText("");
        String conditionalString = "";
        int conditionalCounter = 0;
        for (int i = 0; i < conditionalList.size(); i++) {

            conditionalString = conditionalList.get(i).toString() + "\n";


            if (options.showNumbers())
                conditionalString = i + 1 + ": " + conditionalString;
            conditionalCounter++;


            append(conditionalString);
        }
        description = numberOfConditionals + conditionalCounter;


    }

    public void printCnfcEq(List<ConditionalList> eqClassList, ViewOptions options) {
        Conditional.setSpaceDot(options.showDots());

        int numberOfConditionals = 0;
        setText("");

        for (ConditionalList currentEqList : eqClassList) {
            String currentLine = "";

            for (Conditional currentConditional : currentEqList.getList()) {
                if (options.showNumbers())
                    currentLine = currentLine + currentConditional.getNumber() + ": " + currentConditional.toString();
                else
                    currentLine = currentLine + currentConditional.toString();
                numberOfConditionals++;

            }
            append(currentLine + "\n");
        }
        description = this.numberOfEquivalenceClasses + eqClassList.size() + System.lineSeparator() + "   " + this.numberOfConditionals + numberOfConditionals; //line seperator doesnt work?!
    }


    private String getLineNumber(int i) {
        String lineNumber;
        i = i + 1;
        if (i < 10)
            lineNumber = "0" + i;
        else lineNumber = Integer.toString(i);
        return lineNumber + "   ";
    }

    public String getContentAsString() {
        return getText();
    }

    public String getDescription() {
        return description;
    }


}
