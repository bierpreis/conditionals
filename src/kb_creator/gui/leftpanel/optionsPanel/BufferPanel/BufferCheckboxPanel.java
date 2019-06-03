package kb_creator.gui.leftpanel.optionsPanel.BufferPanel;


import javax.swing.*;

public class BufferCheckboxPanel extends JPanel {
    private JCheckBox saveCheckBox;

    BufferCheckboxPanel() {


        saveCheckBox = new JCheckBox("Buffer Files to Disk");
        add(saveCheckBox);

        saveCheckBox.setEnabled(false);
    }


    public boolean isSelected() {
        return saveCheckBox.isSelected();
    }

    public void setActive(boolean active) {
        saveCheckBox.setEnabled(active);
    }
}
