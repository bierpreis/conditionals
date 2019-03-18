package gui;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CondTextField extends JTextArea {
    private List<Set<Integer>> conditonalsList = new LinkedList<>();

    public CondTextField(List<Set<Integer>> conditonalsList) {
        this.conditonalsList = conditonalsList;
        printConditionals();
    }

    private void printConditionals() {
        for (Set<Integer> setToPrint : conditonalsList)
            append(setToPrint.toString() + "\n");
        System.out.println("printing...");
    }
}
