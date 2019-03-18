package gui;

import Logic.DataContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {
    private CondPanel condPanel;
    private JButton startButton = new JButton("start");

    public StartPanel(CondPanel condPanel, OptionsPanel optionsPanel) {
        this.condPanel = condPanel;

        setBorder(BorderFactory.createTitledBorder("start"));

        startButton.addActionListener(new StartButtonListener(optionsPanel));
        add(startButton);
    }


    class StartButtonListener implements ActionListener {
        private OptionsPanel optionsPanel;

        public StartButtonListener(OptionsPanel optionsPanel) {
            this.optionsPanel = optionsPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DataContainer.createConditionals(optionsPanel.getCardinality());
            condPanel.printConditionals();

        }
    }
}

