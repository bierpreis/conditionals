package view.menu;

import controller.GuiObserver;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MenuPanel extends JPanel {
    private OptionsPanel optionsPanel;

    public MenuPanel(GuiObserver observer) {
        setLayout(new BorderLayout());

        optionsPanel = new OptionsPanel();
        add(optionsPanel, BorderLayout.CENTER);
        add(new StartPanel(observer), BorderLayout.SOUTH);
    }

    public HashMap<String, String> getOptions() {
        return optionsPanel.getOptions();
    }
}
