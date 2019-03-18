package gui;


import javax.swing.*;
import java.util.List;
import java.util.Set;

public class CondPanel extends JPanel {

    public CondPanel(List<Set<Integer>> conditonalsList) {
        add(new CondTextField(conditonalsList));
    }
}
