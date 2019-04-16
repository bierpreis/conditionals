package nfc.view.menu;

import nfc.controller.GuiObserver;
import nfc.view.menu.safe.SafePanel;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MenuPanel extends JPanel {
    private OptionsPanel optionsPanel;

    public MenuPanel(GuiObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        optionsPanel = new OptionsPanel();
        add(optionsPanel, BorderLayout.CENTER);
        add(new SafePanel(observer));
        add(new StartPanel(observer), BorderLayout.SOUTH);
    }

    public HashMap<String, String> getOptions() {
        return optionsPanel.getOptions();
    }
}
