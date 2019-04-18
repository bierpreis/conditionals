package kb_creator.gui;

import javax.swing.*;

public class LeftPanel extends JPanel{


    public LeftPanel() {

        add(new ActionPanel());
        add(new StatusPanel());
    }
}
