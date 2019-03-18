package gui;

import Logic.DataContainer;

import javax.swing.*;
import java.util.List;
import java.util.Set;

public class CondTextField extends JTextArea {
    private List<Set<Integer>> conditonalsList = DataContainer.getCondList();

    public CondTextField() {

    }

    public void printConditionals() {
        for (Set<Integer> setToPrint : DataContainer.getCondList())
            append(setToPrint.toString() + "\n");
        System.out.println("printing...");
    }
}
