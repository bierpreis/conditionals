package view.menu;

import controller.GuiObserver;
import view.textArea.CondPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

public class StartPanel extends JPanel {

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    private final CondPanel condPanel;
    private final JButton createWorldsButton = new JButton("create worlds");
    private final JButton createConditionalsButton = new JButton("create conditionals");
    private final JButton createCnfcButton = new JButton("create cNfc");

    public StartPanel(CondPanel condPanel, GuiObserver observer) {
        this.condPanel = condPanel;

        setBorder(BorderFactory.createTitledBorder("start"));

        createWorldsButton.addActionListener(new CreateWorldsButtonListener());
        createWorldsButton.addActionListener(observer);
        createWorldsButton.setActionCommand("worlds");
        add(createWorldsButton);

        createConditionalsButton.addActionListener(new CreateConditionalsButtonListener());
        createConditionalsButton.addActionListener(observer);
        createConditionalsButton.setActionCommand("conditionals");
        add(createConditionalsButton);


        createCnfcButton.addActionListener(new CreateCnfcButtonListener());
        createCnfcButton.addActionListener(observer);
        createCnfcButton.setActionCommand("nfc");
        add(createCnfcButton);

        changes.addPropertyChangeListener(observer);
    }

    class CreateCnfcButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            changes.firePropertyChange("start", true, true);//todo: this is not perfect
        }
    }


    class CreateWorldsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            changes.firePropertyChange("worlds", true, true);

        }
    }

    class CreateConditionalsButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            changes.firePropertyChange("conditionals", true, false);
        }
    }

}

