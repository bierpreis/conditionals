package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class MainWindow extends JFrame {

    public MainWindow(List<Set<Integer>> conditonalsList) {
        setTitle("NFC Creator");
        add(new WorldsPanel());
        add(new CondTextField(conditonalsList));
        setSize(new Dimension(500, 500));


        pack();
        setVisible(true);
    }
}
