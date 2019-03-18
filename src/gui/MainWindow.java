package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class MainWindow extends JFrame {

    public MainWindow(List<Set<Integer>> conditonalsList) {
        setTitle("NFC Creator");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        add(new MenuPanel());
        add(new CondPanel(conditonalsList));


        setMinimumSize(new Dimension(300, 500));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
