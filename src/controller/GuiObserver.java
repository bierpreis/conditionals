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
    private HashMap options;
    CondPanel condPanel;

    public GuiObserver() {
        MainWindow mainWindow = new MainWindow(this);
        condPanel = mainWindow.getCondPanel();
        options = new HashMap();
    }

    public void propertyChange(PropertyChangeEvent e) {
        options.put(e.getPropertyName(), e.getNewValue());
        System.out.println(options);
        //execCommand(e.);


    }

    private void execCommand(Command command, HashMap options) {
        NfcCreator nfcCreator = new NfcCreator(options);
        switch (command) {

            case WORLDS:
                nfcCreator.createWorlds();
                List<World> worldsList;
                worldsList = nfcCreator.getWorldsList();
                condPanel.printWorlds(worldsList);
                break;

            case CONDITIONALS:
                nfcCreator.createConditionals();
                List<Conditional> conditionalList;
                conditionalList = nfcCreator.getConditionalsList();
                condPanel.printConditionals(conditionalList);
                break;


            case NFC:
                nfcCreator.createcNfc();
                List<ConditionalList> cNfc = nfcCreator.getcNfc();
                condPanel.printCnfc(cNfc);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action!");
        e.getActionCommand();
    }
}
