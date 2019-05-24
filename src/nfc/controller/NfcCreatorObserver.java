package nfc.controller;


import nfc.model.NfcCreator;
import nfc.view.NfcCreatorWindow;
import nfc.view.textArea.CondPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NfcCreatorObserver implements ActionListener {
    private CondPanel condPanel;
    private NfcCreatorWindow mainWindow;

    public NfcCreatorObserver() {
        mainWindow = new NfcCreatorWindow(this);
        condPanel = mainWindow.getCondPanel();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        mainWindow.applySelectedOptions();
        NfcCreator nfcCreator = new NfcCreator(mainWindow.getSignature());

        switch (e.getActionCommand()) {
            case "WORLDS":
                condPanel.printWorlds(nfcCreator.getWorlds());
                break;
            case "NFC":
                condPanel.printConditionals(nfcCreator.getNfc());
                break;
            case "CNFCEQ":
                condPanel.printCnfcEq(nfcCreator.getCnfcEq());
                break;
            case "CNFC":
                condPanel.printConditionals(nfcCreator.getCnfc());
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
