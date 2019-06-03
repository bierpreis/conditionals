package kb_creator.gui.leftpanel.optionsPanel.KBSavePanel;

import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;

public class KbSafePanel extends JPanel {
    private KBCheckboxPanel kbCheckboxPanel;
    private KBLocationPanel kbLocationPanel;

    public KbSafePanel(MainKbSafePanel mainKbSafePanel, ActionPanel actionPanel) {
        kbLocationPanel = new KBLocationPanel();
        kbCheckboxPanel = new KBCheckboxPanel(mainKbSafePanel, actionPanel);
        add(kbCheckboxPanel);

        add(kbLocationPanel);
    }

    public String getFilePath() {
        return kbLocationPanel.getFilePath();
    }

    public void setButtonActive(boolean active) {
        kbLocationPanel.setActive(active);
    }

}
