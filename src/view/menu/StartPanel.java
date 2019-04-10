package view.menu;

import controller.GuiObserver;

import javax.swing.*;

public class StartPanel extends JPanel {


    public StartPanel(GuiObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton createWorldsButton = new JButton("create worlds");
        JButton createConditionalsButton = new JButton("create conditionals");
        JButton createCnfcButton = new JButton("create cNfc");

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
    }


}

