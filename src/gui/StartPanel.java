package gui;

import Logic.DataContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {
    private CondPanel condPanel;
    private JButton startButton = new JButton("start");

    public StartPanel(CondPanel condPanel) {
        this.condPanel = condPanel;

        setBorder(BorderFactory.createTitledBorder("start"));

        startButton.addActionListener(new StartButtonListener());
        add(startButton);
    }


    class StartButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DataContainer.createConditionals(4);
            condPanel.printConditionals();

        }
    }
}

