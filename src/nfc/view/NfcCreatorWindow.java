package nfc.view;

import nfc.controller.NfcCreatorObserver;
import nfc.view.menu.MenuPanel;
import nfc.view.textArea.CondPanel;
import nfc.view.textArea.ViewOptions;

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

    public ViewOptions getOptions() {
        return menuPanel.getOptions();
    }

    public String getSignature(){
        return menuPanel.getSignature();
    }
}
