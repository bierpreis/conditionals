package view;

import view.menu.MenuPanel;
import view.textArea.CondPanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("NFC Creator");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        CondPanel condPanel = new CondPanel();


        add(new MenuPanel(condPanel));
        getContentPane().add(condPanel);


        setMinimumSize(new Dimension(300, 500));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
