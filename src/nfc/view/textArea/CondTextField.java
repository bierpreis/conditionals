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
        setText("");
        String conditionalString = "";
        int conditionalCounter = 0;
        for (int i = 0; i < conditionalList.size(); i++) {
            if (options.showNumbers())
                conditionalString = i + 1 + ": " + conditionalList.get(i).toString() + "\n";
            else conditionalString = conditionalList.get(i).toString() + "\n";
            conditionalCounter++;

            if (options.showLetters())
                conditionalString = translateNumbersToLetters(conditionalString, options);

            append(conditionalString);
        }
        description = numberOfConditionals + conditionalCounter;


    }

    //todo: put shownumbers here somehow
    public void printCnfcEq(List<ConditionalList> conditionalList, ViewOptions options) {
        int numberOfConditionals = 0;
        setText("");
        for (int i = 0; i < conditionalList.size(); i++) {
            ConditionalList currentList = conditionalList.get(i);
            numberOfConditionals = numberOfConditionals + currentList.getSize();
            append(currentList.toString() + "\n");
        }
        description = this.numberOfEquivalenceClasses + conditionalList.size() + System.lineSeparator() + "   " + this.numberOfConditionals + numberOfConditionals; //line seperator doesnt work?!
    }

    public void printCnfc(List<ConditionalList> conditionalList) {
        int numberOfConditionals = 0;
        setText("");
        for (int i = 0; i < conditionalList.size(); i++) {
            ConditionalList currentList = conditionalList.get(i);
            numberOfConditionals = numberOfConditionals + currentList.getSize();
            append(currentList.get(0) + "\n");

        }
        description = this.numberOfEquivalenceClasses + conditionalList.size() + System.lineSeparator() + "   " + this.numberOfConditionals + numberOfConditionals; //line seperator doesnt work?!
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

    public String translateNumbersToLetters(String string, ViewOptions options) {


        if (!options.showNumbers()) {
            if (!options.isTwoLetters()) {

                string = string.replace("0", "!a!b!c");
                string = string.replace("1", "!a!bc");
                string = string.replace("2", "!ab!c");
                string = string.replace("3", "!abc");
                string = string.replace("4", "a!b!c");
                string = string.replace("5", "a!bc");
                string = string.replace("6", "ab!c");
                string = string.replace("7", "abc");

            } else {
                string = string.replace("0", "!a!b");
                string = string.replace("1", "!ab");
                string = string.replace("2", "a!b");
                string = string.replace("3", "ab");


            }
        }

        return string;
    }

}
