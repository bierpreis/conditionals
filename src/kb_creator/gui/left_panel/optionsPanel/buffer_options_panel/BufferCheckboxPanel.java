package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;


import javax.swing.*;
import java.awt.*;

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

    public void setBoxEnabled(boolean active) {

        saveCheckBox.setEnabled(active);
    }

    public void setBoxSelected(boolean selected) {


        saveCheckBox.setSelected(selected);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }


}
