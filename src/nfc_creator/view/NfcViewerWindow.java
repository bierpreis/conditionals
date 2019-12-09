package nfc_creator.view;

import kb_creator.model.logic.signature.AbstractSignature;
import nfc_creator.controller.NfcCreatorObserver;
import nfc_creator.view.menu.MenuPanel;
import nfc_creator.view.textArea.CondPanel;

import javax.swing.*;
import java.awt.*;

public class NfcViewerWindow {

    private CondPanel condPanel;
    private MenuPanel menuPanel;

    public NfcViewerWindow(NfcCreatorObserver observer) {
        JFrame mainWindow;
        mainWindow = new JFrame();
        mainWindow.setTitle("NFC Viewer");

        mainWindow.setLayout(new BorderLayout());

        condPanel = new CondPanel();


        mainWindow.add(menuPanel = new MenuPanel(observer), BorderLayout.WEST);
        mainWindow.getContentPane().add(condPanel, BorderLayout.CENTER);

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainWindow.pack();
        mainWindow.setVisible(true);

        mainWindow.setLocationRelativeTo(null);
    }

    public CondPanel getCondPanel() {
        return condPanel;
    }


    public AbstractSignature getSignature() {
        return menuPanel.getSignature();
    }

    public void applySelectedOptions() {
        menuPanel.applySelectedOptions();
    }
}
