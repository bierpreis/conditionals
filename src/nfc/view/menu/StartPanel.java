package nfc.view.menu;

import nfc.controller.NfcCreatorObserver;
import nfc.model.Action;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {


    public StartPanel(NfcCreatorObserver observer) {
        setLayout(new GridLayout());
        setBorder(BorderFactory.createTitledBorder("start"));

        AbstractActionButton createNfcButton = new AbstractActionButton(Action.NFC);

        AbstractActionButton createCnfcButton = new AbstractActionButton(Action.CNFC);
        AbstractActionButton createCnfcEqButton = new AbstractActionButton(Action.CNFCEQ);


        createNfcButton.addActionListener(observer);
        add(createNfcButton);

        createCnfcButton.addActionListener(observer);
        add(createCnfcButton);

        createCnfcEqButton.addActionListener(observer);
        add(createCnfcEqButton);

    }


}

