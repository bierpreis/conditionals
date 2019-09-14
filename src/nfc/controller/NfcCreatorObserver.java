package nfc.controller;


import nfc.model.NfcCreator;
import nfc.view.NfcViewerWindow;
import nfc.view.textArea.CondPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NfcCreatorObserver implements ActionListener {
    private CondPanel condPanel;
    private NfcViewerWindow mainWindow;

    public NfcCreatorObserver() {
        mainWindow = new NfcViewerWindow(this);
        condPanel = mainWindow.getCondPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainWindow.applySelectedOptions();
        NfcCreator nfcCreator = new NfcCreator(mainWindow.getSignature());

        switch (e.getActionCommand()) {
            case "WORLDS":
                condPanel.printWorlds(nfcCreator.getWorldList());
                break;
            case "WORLDS_FORMULAS":
                condPanel.printWorldsAndFormulas(nfcCreator.getWorldList(), nfcCreator.getWorldsFormulasMap());
                break;
            case "CONDITIONALS":
                condPanel.printConditionals(nfcCreator.getBasicConditionals());
                break;
            case "CNFCEQ":
                condPanel.printCnfcEq(nfcCreator.getOldCnfcEq());
                break;
            case "CNFC":
                condPanel.printConditionals(nfcCreator.getOldCnfc());
                break;
            case "NFC":
                condPanel.printConditionals(nfcCreator.getOldNfc());
                break;
            case "NFC_COUNTER":
                condPanel.printConditionalsWithCounters(nfcCreator.getOldNfc());
                break;
            default:
                throw new RuntimeException("Unknown command: " + e.getActionCommand());

        }
    }

    public void saveFile(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(condPanel.getContentAsString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
