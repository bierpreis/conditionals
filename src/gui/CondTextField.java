package gui;

import Logic.DataContainer;

import javax.swing.*;
import java.util.Set;

public class CondTextField extends JTextArea {

    public CondTextField() {

    }

    public void printConditionals() {
        for (Set<Integer> setToPrint : DataContainer.getCondList())
            append(setToPrint.toString() + "\n");
        System.out.println("printing...");
    }
}
