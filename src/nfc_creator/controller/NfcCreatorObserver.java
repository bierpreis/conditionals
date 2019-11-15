package nfc_creator.controller;


import nfc_creator.model.NfcCreator;
import nfc_creator.view.NfcViewerWindow;
import nfc_creator.view.textArea.CondPanel;

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
                condPanel.printWorlds(nfcCreator.getWorldsList());
                break;
            case "WORLDS_FORMULAS":
                condPanel.printWorldsAndFormulas(nfcCreator.getWorldsList(), nfcCreator.createWorldsFormulasMap());
                break;
            case "CONDITIONALS":
                condPanel.printConditionals(nfcCreator.getWConditionalList());
                break;
            case "CNFCEQ":
                condPanel.printCnfcEq(nfcCreator.getwCnfcEq());
                break;
            case "CNFC":
                condPanel.printConditionals(nfcCreator.getwCnfc());
                break;
            case "NFC":
                condPanel.printConditionals(nfcCreator.getwNfc());
                break;
            case "NFC_COUNTER":
                condPanel.printConditionalsWithCounters(nfcCreator.getwNfc());
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
