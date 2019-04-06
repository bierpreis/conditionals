package view.textArea;

import model.Conditional;
import model.ConditionalList;
import model.World;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CondTextField extends JTextArea {
    private final String numberOfWorlds = "Worlds: ";
    private final String numberOfConditionals = "Conditionals: ";
    private final String numberOfEquivalenceClasses = "Equivalence Classes: ";

    private String description;

    public CondTextField() {
        super(40, 40);
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
        int conditionalCounter = 0;
        for (int i = 0; i < conditionalList.size(); i++) {
            append(conditionalList.get(i).toString() + "\n");
            conditionalCounter++;
        }
        description = numberOfConditionals + conditionalCounter;
    }

    public void printCnfc(List<ConditionalList> conditionalList) {
        int numberOfNfc = 0;
        setText("");
        for (int i = 0; i < conditionalList.size(); i++) {
            ConditionalList currentList = conditionalList.get(i);
            append(currentList.toString() + "\n");
            numberOfNfc++;
        }
        description = numberOfEquivalenceClasses + numberOfNfc + System.lineSeparator() + numberOfConditionals + conditionalList.size(); //line seperator doesnt work?!
    }

    private String getLineNumber(int i) {
        String lineNumber;
        i = i + 1;
        if (i < 10)
            lineNumber = "0" + i;
        else lineNumber = Integer.toString(i);
        return lineNumber + "   ";
    }

    public String getDescription() {
        return description;
    }
}
