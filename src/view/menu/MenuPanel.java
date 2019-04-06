package view.menu;

import controller.GuiObserver;


import javax.swing.*;
import java.util.HashMap;

public class MenuPanel extends JPanel {
    OptionsPanel optionsPanel;

    public MenuPanel(GuiObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        optionsPanel = new OptionsPanel(observer);
        add(optionsPanel);
        add(new StartPanel(observer));
    }

    public HashMap<String, String> getOptions() {
        return optionsPanel.getOptions();
    }
}
