package view.menu;

import controller.GuiObserver;


import javax.swing.*;
import java.util.HashMap;

public class MenuPanel extends JPanel {
    private OptionsPanel optionsPanel;

    public MenuPanel(GuiObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        optionsPanel = new OptionsPanel();
        add(optionsPanel);
        add(new StartPanel(observer));
    }

    public HashMap<String, String> getOptions() {
        return optionsPanel.getOptions();
    }
}
