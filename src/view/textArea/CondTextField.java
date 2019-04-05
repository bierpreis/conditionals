package view.textArea;

import model.ConditionalList;
import model.DataContainer;
import model.World;

import javax.swing.*;
import java.util.List;

public class CondTextField extends JTextArea {
    private final String numberOfWorlds = "Worlds: ";
    private final String numberOfConditionals = "Conditionals: ";
    private final String numberOfEquivalenceClasses = "Equivalence Classes: ";

    private String description;

    public CondTextField() {
        super(40, 40);
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

    public void printConditionals() {
        setText("");
        int conditionalCounter = 0;
        for (int i = 0; i < DataContainer.getConditionalSet().size(); i++) {
            append(getLineNumber(i) + DataContainer.getConditionalSet().get(i).toString() + "\n");
            conditionalCounter++;
        }
        description = numberOfConditionals + conditionalCounter;
    }

    public void printCnfc() {
        int numberOfNfc = 0;
        setText("");
        for (int i = 0; i < DataContainer.getCnfc().size(); i++) {
            ConditionalList currentList = DataContainer.getCnfc().get(i);
            append(getLineNumber(i) + currentList.toString() + "\n");
            numberOfNfc++;
        }
        description = numberOfEquivalenceClasses + numberOfNfc + System.lineSeparator() + numberOfConditionals + DataContainer.getCnfc().size(); //line seperator doesnt work?!
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
