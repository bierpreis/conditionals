package view.menu;

import controller.GuiObserver;
import view.textArea.CondPanel;

import javax.swing.*;

public class MenuPanel extends JPanel {

    public MenuPanel(GuiObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        OptionsPanel optionsPanel = new OptionsPanel(observer);
        add(optionsPanel);
        add(new StartPanel(observer));
    }
}
