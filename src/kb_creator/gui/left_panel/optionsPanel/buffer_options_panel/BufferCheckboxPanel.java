package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class BufferCheckboxPanel extends JPanel {
    private JCheckBox saveCheckBox;
    private MainBufferPanel mainBufferPanel;

    BufferCheckboxPanel(MainBufferPanel mainBufferPanel) {

        saveCheckBox = new JCheckBox("Buffer Files to Disk");
        add(saveCheckBox);

        saveCheckBox.setEnabled(false);

        saveCheckBox.addChangeListener(new CheckBoxActionListener());
        this.mainBufferPanel = mainBufferPanel;
    }


    public boolean isSelected() {
        return saveCheckBox.isSelected();
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

    class CheckBoxActionListener implements ChangeListener {


        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            if (!saveCheckBox.isSelected()) {
                mainBufferPanel.getBufferCheckboxPanel().setBoxSelected(false);
                mainBufferPanel.getBufferCheckboxPanel().setBoxEnabled(false);
                mainBufferPanel.getBufferSizePanel().setEnabled(false);
                mainBufferPanel.getDeleteCheckbox().setEnabled(false);
                mainBufferPanel.getFileNameLengthPanel().setEnabled(false);
            }

            //checkbox should disable itself if deselected
            if (saveCheckBox.isSelected())
                mainBufferPanel.getBufferCheckboxPanel().setBoxEnabled(true);
        }
    }


}
