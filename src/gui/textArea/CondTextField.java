package gui.textArea;

import Logic.ConditionalList;
import Logic.DataContainer;

import javax.swing.*;

public class CondTextField extends JTextArea {

    public CondTextField() {
        super(40, 40);
    }


    public int printWorlds() {
        setText("");
        int numberCounter = 0;
        for (int i = 0; i < DataContainer.getWorldsList().size(); i++) {
            append(i + 1 + "    " + DataContainer.getWorldsList().get(i).toString() + "\n");
            numberCounter++;

        }
        return numberCounter;
    }

    public int printConditionals() {
        setText("");
        int numberOfConditionals = 0;
        for (int i = 0; i < DataContainer.getConditionalSet().size(); i++) {
            append(i + 1 + "    " + DataContainer.getConditionalSet().get(i).toString() + "\n");
            numberOfConditionals++;
        }
        return numberOfConditionals;
    }

    public int printCnfc() {
        int numberOfNfc = 0;
        setText("");
        for (int i = 0; i < DataContainer.getCnfc().size(); i++) {
            ConditionalList currentList = DataContainer.getCnfc().get(i);
            append(i + 1 + "   " + currentList.toString() + "\n");
            numberOfNfc++;
        }
        return numberOfNfc;
    }
}
