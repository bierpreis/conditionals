package view.menu;

import controller.GuiObserver;
import view.menu.safe.SafePanel;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.HashMap;

public class MenuPanel extends JPanel {
    private OptionsPanel optionsPanel;

    public MenuPanel(GuiObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        optionsPanel = new OptionsPanel();
        add(optionsPanel, BorderLayout.CENTER);
        add(new SafePanel());
        add(new StartPanel(observer), BorderLayout.SOUTH);
    }

    public HashMap<String, String> getOptions() {
        return optionsPanel.getOptions();
    }
}
