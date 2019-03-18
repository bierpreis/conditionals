package gui;

import javax.swing.*;

public class MenuPanel extends JPanel {

    public MenuPanel(CondPanel conditionalsPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new WorldsPanel());
        add(new StartPanel(conditionalsPanel));
    }
}
