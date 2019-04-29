package nfc.view;

import nfc.controller.NfcCreatorObserver;
import nfc.view.menu.MenuPanel;
import nfc.view.textArea.CondPanel;

import javax.swing.*;
import java.awt.*;

public class NfcCreatorWindow {
    private JFrame mainWindow;
    private CondPanel condPanel;
    private MenuPanel menuPanel;

    public NfcCreatorWindow(NfcCreatorObserver observer) {
        mainWindow = new JFrame();
        mainWindow.setTitle("NFC Creator");

        mainWindow.setLayout(new BorderLayout());

        condPanel = new CondPanel();


        mainWindow.add(menuPanel = new MenuPanel(observer), BorderLayout.WEST);
        mainWindow.getContentPane().add(condPanel, BorderLayout.CENTER);

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    public CondPanel getCondPanel() {
        return condPanel;
    }


    public String getSignature(){
        return menuPanel.getSignature();
    }

    public void applySelectedOptions(){
        menuPanel.applySelectedOptions();
    }
}
