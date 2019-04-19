package nfc.controller;


import nfc.model.NfcCreator;
import nfc.view.NfcCreatorWindow;
import nfc.view.textArea.CondPanel;
import nfc.view.textArea.CondTextField;
import nfc.view.textArea.ViewOptions;

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
        NfcCreator nfcCreator = new NfcCreator(mainWindow.getSignature());

        ViewOptions options = mainWindow.getOptions();
        switch (e.getActionCommand()) {
            case "NFC":
                condPanel.printConditionals(nfcCreator.getNfc(), options);
                break;
            case "CNFCEQ":
                condPanel.printCnfcEq(nfcCreator.getCnfcEq(), options);
                break;
            case "CNFC":
                condPanel.printConditionals(nfcCreator.getCnfc(), options);
                break;

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
