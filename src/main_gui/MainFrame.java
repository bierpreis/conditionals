package main_gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(){
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        JPanel mainPanel = new JPanel();

        JPanel buttonPanel = new JPanel();

        mainPanel.setLayout(new FlowLayout());

        setTitle("Conditional Knowledge Base Creator");


        mainPanel.add(new JLabel("Choose what to do"));

        mainPanel.setPreferredSize((new Dimension(600, 80)));

        buttonPanel.setPreferredSize((new Dimension(600, 80)));

        buttonPanel.add(new NfcButton(this));

        buttonPanel.add(new KbButton(this));


        add(mainPanel);

        add(buttonPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        pack();

        setVisible(true);

    }
}
