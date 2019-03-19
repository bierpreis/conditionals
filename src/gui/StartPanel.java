package gui;

import Logic.DataContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {
    private CondPanel condPanel;
    private JButton createWorldsButton = new JButton("create worlds");
    private JButton createConditionalsButton = new JButton("create conditionals");

    public StartPanel(CondPanel condPanel, OptionsPanel optionsPanel) {
        this.condPanel = condPanel;

        setBorder(BorderFactory.createTitledBorder("start"));

        createWorldsButton.addActionListener(new CreateWorldsButtonListener(optionsPanel));
        add(createWorldsButton);

        createConditionalsButton.addActionListener(new CreateConditionalsButtonListener());
        add(createConditionalsButton);
    }


    class CreateWorldsButtonListener implements ActionListener {
        private OptionsPanel optionsPanel;

        public CreateWorldsButtonListener(OptionsPanel optionsPanel) {
            this.optionsPanel = optionsPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DataContainer.createWorlds(optionsPanel.getCardinality());
            condPanel.printWorlds();

        }
    }

    class CreateConditionalsButtonListener implements ActionListener {

        public CreateConditionalsButtonListener() {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DataContainer.createConditionals();
            condPanel.printConditionals();
        }
    }
}

