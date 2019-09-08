package nfc.view.menu;

import nfc.controller.NfcCreatorObserver;
import nfc.model.Action;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {


    public StartPanel(NfcCreatorObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setLayout(new GridLayout());
        setBorder(BorderFactory.createTitledBorder("start"));

        JPanel basicPanel = new JPanel();
        add(basicPanel);

        JPanel nfcPanel = new JPanel();
        add(nfcPanel);

        AbstractActionButton createWorldsButton = new AbstractActionButton(Action.WORLDS);

        AbstractActionButton createWorldsFormulasButton = new AbstractActionButton(Action.WORLDS_FORMULAS);

        AbstractActionButton createBasicConditional = new AbstractActionButton(Action.CONDITIONALS);

        AbstractActionButton createCnfcButton = new AbstractActionButton(Action.CNFC);
        AbstractActionButton createCnfcEqButton = new AbstractActionButton(Action.CNFCEQ);

        AbstractActionButton createNfcButton = new AbstractActionButton(Action.NFC);

        AbstractActionButton createNfcWithCounterButton = new AbstractActionButton(Action.NFC_COUNTER);

        createWorldsButton.addActionListener(observer);
        basicPanel.add(createWorldsButton);

        createWorldsFormulasButton.addActionListener(observer);
        basicPanel.add(createWorldsFormulasButton);

        createBasicConditional.addActionListener(observer);
        basicPanel.add(createBasicConditional);

        createCnfcButton.addActionListener(observer);
        nfcPanel.add(createCnfcButton);

        createCnfcEqButton.addActionListener(observer);
        nfcPanel.add(createCnfcEqButton);

        createNfcButton.addActionListener(observer);
        nfcPanel.add(createNfcButton);

        createNfcWithCounterButton.addActionListener(observer);
        nfcPanel.add(createNfcWithCounterButton);
    }


}

