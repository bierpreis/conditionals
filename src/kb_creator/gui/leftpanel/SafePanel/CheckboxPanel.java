package kb_creator.gui.leftpanel.SafePanel;

import javax.swing.*;

public class CheckboxPanel extends JPanel {
    private JCheckBox checkBox;

    public CheckboxPanel() {
        checkBox = new JCheckBox("Save KBs to File");
        add(checkBox);
    }

    boolean isSavingRequested() {
        return checkBox.isEnabled();
    }
}
