package gui;

import Logic.Conditional;
import Logic.DataContainer;

import javax.swing.*;
import java.awt.*;
import java.util.Set;


public class CondTextField extends JTextArea {

    public CondTextField() {
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }


    public void printWorlds() {

        for (Set<Integer> setToPrint : DataContainer.getWorldsList())
            append(setToPrint.toString() + "\n");
    }

    public void printConditionals() {
        for (Conditional conditional : DataContainer.getConditionalSet())
            append(conditional.toString() + "\n");
    }
}
