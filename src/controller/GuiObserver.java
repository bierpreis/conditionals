package controller;

import model.Conditional;
import model.ConditionalList;
import model.NfcCreator;
import model.World;
import view.MainWindow;
import view.textArea.CondPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;

public class GuiObserver implements PropertyChangeListener, ActionListener {
    private HashMap options; //todo this waring
    private CondPanel condPanel;
    private MainWindow mainWindow;

    public GuiObserver() {
        mainWindow = new MainWindow(this);
        condPanel = mainWindow.getCondPanel();
        options = new HashMap();
    }

    public void propertyChange(PropertyChangeEvent e) {
        options.put(e.getPropertyName(), e.getNewValue());
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        HashMap<String, String> options = mainWindow.getOptions();
        NfcCreator nfcCreator = new NfcCreator(options);
        switch (e.getActionCommand()) {
            case "nfc":
                List<ConditionalList> cNfc = nfcCreator.createcNfc(options.get("signature"));
                condPanel.printCnfc(cNfc);
                break;
            case "conditionals":
                List<Conditional> conditionalList = nfcCreator.createConditionals(options.get("signature"));
                condPanel.printConditionals(conditionalList);
                break;
            case "worlds":
                List<World> worldsList = nfcCreator.createWorlds(options.get("signature"));
                condPanel.printWorlds(worldsList);
                break;
        }
    }
}
