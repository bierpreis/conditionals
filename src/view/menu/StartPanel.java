package view.menu;

import controller.GuiObserver;

import javax.swing.*;

import java.beans.PropertyChangeSupport;

public class StartPanel extends JPanel {

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    private final JButton createWorldsButton = new JButton("create worlds");
    private final JButton createConditionalsButton = new JButton("create conditionals");
    private final JButton createCnfcButton = new JButton("create cNfc");

    public StartPanel(GuiObserver observer) {

        setBorder(BorderFactory.createTitledBorder("start"));

        createWorldsButton.addActionListener(observer);
        createWorldsButton.setActionCommand("worlds");
        add(createWorldsButton);

        createConditionalsButton.addActionListener(observer);
        createConditionalsButton.setActionCommand("conditionals");
        add(createConditionalsButton);

        createCnfcButton.addActionListener(observer);
        createCnfcButton.setActionCommand("nfc");
        add(createCnfcButton);

        changes.addPropertyChangeListener(observer);
    }


}

