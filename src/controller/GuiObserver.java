package controller;

import model.*;
import view.MainWindow;
import view.textArea.CondPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GuiObserver implements ActionListener {
    private CondPanel condPanel;
    private MainWindow mainWindow;

    public GuiObserver() {
        mainWindow = new MainWindow(this);
        condPanel = mainWindow.getCondPanel();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        HashMap<String, String> options = mainWindow.getOptions();
        NfcCreator nfcCreator = new NfcCreator(options);
        //todo: this is not great
        //todo: remove signature
        switch (e.getActionCommand()) {
            case "NFC":
                List<Conditional> nfc = nfcCreator.createConditionals(options.get("signature"));
                condPanel.printConditionals(nfc);
                break;
            case "CNFCEQ": { //brackets to create own name scope for cnfc
                List<ConditionalList> cNfc = nfcCreator.createCnfcEq(options.get("signature"));
                condPanel.printCnfcEq(cNfc);
                break;
            }
            case "CNFC": {
                List<ConditionalList> cNfc = nfcCreator.createCnfcEq(options.get("signature"));
                condPanel.printCnfc(cNfc);
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
