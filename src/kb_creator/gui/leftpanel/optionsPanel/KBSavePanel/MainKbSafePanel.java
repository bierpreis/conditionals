package kb_creator.gui.leftpanel.optionsPanel.KBSavePanel;


import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;
import java.awt.*;

public class MainKbSafePanel extends JPanel {

    private KbSafePanel kbSafePanel;


    public MainKbSafePanel() {
        System.out.println("create mainsafepanel");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Safe Options"));

        this.kbSafePanel = new KbSafePanel(this);
        add(kbSafePanel);

    }

    public String getFileLocation() {
        return kbSafePanel.getFilePath();

    }

    public void setButtonActive(boolean active) {
        this.kbSafePanel.setButtonActive(active);
    }

}
