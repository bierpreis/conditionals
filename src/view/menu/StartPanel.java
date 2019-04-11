package view.menu;

import controller.GuiObserver;

import javax.swing.*;

public class StartPanel extends JPanel {


    public StartPanel(GuiObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("start"));

        JButton createNfcButton = new JButton("NFC");
        JButton createCnfcButton = new JButton("cNFC");
        JButton createCnfcEqButton = new JButton("cNFC with EQ");


        createNfcButton.addActionListener(observer);
        createNfcButton.setActionCommand("conditionals");
        add(createNfcButton);

        createCnfcButton.addActionListener(observer);
        createCnfcButton.setActionCommand("nfc");
        add(createCnfcButton);

        //todo: create function
        createCnfcEqButton.addActionListener(observer);
        createCnfcEqButton.setActionCommand("cnfcEQ");
        add(createCnfcEqButton);

    }


}

