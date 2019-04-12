package controller;

import model.*;
import view.MainWindow;
import view.textArea.CondPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            case "CNFCEQ":
                List<ConditionalList> cNfc = nfcCreator.createCnfcEq(options.get("signature"));
                condPanel.printCnfc(cNfc);
                break;
            case "worlds":
                List<World> worldsList = nfcCreator.createWorlds(options.get("signature"));
                condPanel.printWorlds(worldsList);
                break;
        }
    }
}
