package gui;

import Logic.DataContainer;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class CondTextField extends JTextArea {

    public CondTextField() {
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }


    public void printConditionals() {

        for (Set<Integer> setToPrint : DataContainer.getCondList())
            append(setToPrint.toString() + "\n");
    }
}
