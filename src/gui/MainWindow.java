package gui;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("NFC Creator");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        CondPanel condPanel = new CondPanel();


        add(new MenuPanel(condPanel));
        add(condPanel);


        setMinimumSize(new Dimension(300, 500));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
