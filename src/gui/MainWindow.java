package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class MainWindow extends JFrame {

    public MainWindow(List<Set<Integer>> conditonalsList) {
        setTitle("NFC Creator");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        add(new WorldsPanel());
        add(new CondPanel(conditonalsList));

        setMinimumSize(new Dimension(300, 500));


        pack();
        setVisible(true);
    }
}
