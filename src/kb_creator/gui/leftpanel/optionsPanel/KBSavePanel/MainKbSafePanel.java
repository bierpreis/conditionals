package kb_creator.gui.leftpanel.optionsPanel.KBSavePanel;


import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;
import java.awt.*;

public class MainKbSafePanel extends JPanel {
    private KBCheckboxPanel kbCheckboxPanel;
    private KBLocationPanel kbLocationPanel;


    public MainKbSafePanel() {
        setBorder(BorderFactory.createTitledBorder("Knowledge Base Save Options"));


        kbCheckboxPanel = new KBCheckboxPanel(this);
        kbLocationPanel = new KBLocationPanel(kbCheckboxPanel);


        add(kbLocationPanel);
        add(kbCheckboxPanel);

    }

    public String getFileLocation() {
        return kbLocationPanel.getFilePath();

    }

    public void setButtonActive(boolean active) {
        kbCheckboxPanel.setActive(active);
    }

}
