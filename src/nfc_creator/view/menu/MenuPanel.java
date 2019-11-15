package nfc_creator.view.menu;

import kb_creator.model.propositional_logic.signature.AbstractSignature;
import nfc_creator.controller.NfcCreatorObserver;
import nfc_creator.view.menu.options.OptionsPanel;


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


    public AbstractSignature getSignature() {
        return optionsPanel.getSignature();
    }

    public void applySelectedOptions() {
        optionsPanel.applySelectedOptions();
    }
}
