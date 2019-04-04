package view.menu;

import view.textArea.CondPanel;

import javax.swing.*;

public class MenuPanel extends JPanel {

    public MenuPanel(CondPanel conditionalsPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        OptionsPanel optionsPanel = new OptionsPanel();
        add(optionsPanel);
        add(new StartPanel(conditionalsPanel, optionsPanel));
    }
}
