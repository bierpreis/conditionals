package gui;

import Logic.DataContainer;

import javax.swing.*;
import java.util.Set;

public class CondTextField extends JTextArea {


    public void printConditionals() {
        for (Set<Integer> setToPrint : DataContainer.getCondList())
            append(setToPrint.toString() + "\n");
    }
}
