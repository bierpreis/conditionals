package gui.menu;

import Logic.DataContainer;
import Logic.World;
import Logic.WorldDiffrence;
import gui.menu.OptionsPanel;
import gui.textArea.CondPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {
    private CondPanel condPanel;
    private JButton createWorldsButton = new JButton("create worlds");
    private JButton createConditionalsButton = new JButton("create conditionals");
    private JButton createCnfcButton = new JButton("create cNfc");

    public StartPanel(CondPanel condPanel, OptionsPanel optionsPanel) {
        this.condPanel = condPanel;

        setBorder(BorderFactory.createTitledBorder("start"));

        createWorldsButton.addActionListener(new CreateWorldsButtonListener(optionsPanel));
        add(createWorldsButton);

        createConditionalsButton.addActionListener(new CreateConditionalsButtonListener(optionsPanel));
        add(createConditionalsButton);

        createCnfcButton.addActionListener(new CreateCnfcButtonListener(optionsPanel));
        add(createCnfcButton);
    }

    class CreateCnfcButtonListener implements ActionListener {
        OptionsPanel optionsPanel;

        public CreateCnfcButtonListener(OptionsPanel optionsPanel) {
            this.optionsPanel = optionsPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DataContainer.createCnfc(optionsPanel.getSignature());
            condPanel.printCnfc();
        }
    }


    class CreateWorldsButtonListener implements ActionListener {
        private OptionsPanel optionsPanel;

        public CreateWorldsButtonListener(OptionsPanel optionsPanel) {
            this.optionsPanel = optionsPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DataContainer.createWorlds(optionsPanel.getSignature());
            condPanel.printWorlds();

        }
    }

    class CreateConditionalsButtonListener implements ActionListener {
        private OptionsPanel optionsPanel;

        public CreateConditionalsButtonListener(OptionsPanel optionsPanel) {
            this.optionsPanel = optionsPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            WorldDiffrence.setSignature(optionsPanel.getSignature());
            DataContainer.createConditionals(optionsPanel.getSignature()); //todo: remove?
            condPanel.printConditionals();
        }
    }
}

