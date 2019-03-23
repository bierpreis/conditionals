package gui;

import Logic.DataContainer;

import javax.swing.*;
import java.util.List;
import java.util.Set;


public class CondTextField extends JTextArea {

    public CondTextField() {
        super(40, 40);
    }


    public void printWorlds() {
        setText("");
        for (List<Integer> setToPrint : DataContainer.getWorldsList())
            append(setToPrint.toString() + "\n");
    }

    public void printConditionals() {
        setText("");
        for (int i = 0; i < DataContainer.getConditionalSet().size(); i++)
            append(i + 1 + "    " + DataContainer.getConditionalSet().get(i).toString() + "\n");

        System.out.println("conditionals printed: " + DataContainer.getConditionalSet().size());
    }
}
