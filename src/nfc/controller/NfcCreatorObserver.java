package nfc.controller;

import nfc.model.Conditional;
import nfc.model.ConditionalList;
import nfc.model.NfcCreator;
import nfc.view.NfcCreatorWindow;
import nfc.view.textArea.CondPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class NfcCreatorObserver implements ActionListener {
    private CondPanel condPanel;
    private NfcCreatorWindow mainWindow;

    public NfcCreatorObserver() {
        mainWindow = new NfcCreatorWindow(this);
        condPanel = mainWindow.getCondPanel();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        HashMap<String, String> options = mainWindow.getOptions();
        NfcCreator nfcCreator = new NfcCreator(mainWindow.getSignature());
        //todo: remove signature and options
        switch (e.getActionCommand()) {
            case "NFC":
                List<Conditional> nfc = nfcCreator.createNfc(options.get("signature"));
                condPanel.printConditionals(nfc);
                break;
            case "CNFCEQ": { //brackets to create own name scope for cnfc
                List<ConditionalList> cNfc = nfcCreator.createCnfcEq(options.get("signature"));
                condPanel.printCnfcEq(cNfc);
                break;
            }
            case "CNFC": {
                List<Conditional> cNfc = nfcCreator.createCnfc(options.get("signature"));
                condPanel.printConditionals(cNfc);
                break;
            }
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
