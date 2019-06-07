package nfc.view.menu;

import nfc.controller.NfcCreatorObserver;
import nfc.model.Action;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {


    public StartPanel(NfcCreatorObserver observer) {
        setLayout(new GridLayout());
        setBorder(BorderFactory.createTitledBorder("start"));

        AbstractActionButton createWorldsButton = new AbstractActionButton(Action.WORLDS);

        AbstractActionButton createBasicConditional = new AbstractActionButton(Action.CONDITIONALS);

        AbstractActionButton createCnfcButton = new AbstractActionButton(Action.CNFC);
        AbstractActionButton createCnfcEqButton = new AbstractActionButton(Action.CNFCEQ);

        AbstractActionButton createNfcButton = new AbstractActionButton(Action.NFC);

        AbstractActionButton createNfcWithCounterButton = new AbstractActionButton(Action.NFC_COUNTER);

        createWorldsButton.addActionListener(observer);
        add(createWorldsButton);

        createBasicConditional.addActionListener(observer);
        add(createBasicConditional);

        createCnfcButton.addActionListener(observer);
        add(createCnfcButton);

        createCnfcEqButton.addActionListener(observer);
        add(createCnfcEqButton);

        createNfcButton.addActionListener(observer);
        add(createNfcButton);

        createNfcWithCounterButton.addActionListener(observer);
        add(createNfcWithCounterButton);

        //todo: button to show nfc with counter conditionals

    }


}

