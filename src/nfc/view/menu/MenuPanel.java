package nfc.view.menu;

import nfc.controller.NfcCreatorObserver;
import nfc.view.menu.options.OptionsPanel;
import nfc.view.textArea.ViewOptions;


import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private OptionsPanel optionsPanel;

    public MenuPanel(NfcCreatorObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        optionsPanel = new OptionsPanel();
        add(optionsPanel, BorderLayout.CENTER);
        add(new SafePanel(observer));
        add(new StartPanel(observer), BorderLayout.SOUTH);
    }

    public ViewOptions getOptions() {
        return optionsPanel.applySelectedOptions();
    }

    public String getSignature(){
        return optionsPanel.getSignature();
    }
}
