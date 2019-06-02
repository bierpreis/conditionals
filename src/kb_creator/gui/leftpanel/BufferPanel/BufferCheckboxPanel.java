package kb_creator.gui.leftpanel.BufferPanel;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BufferCheckboxPanel extends JPanel {
    private BufferLocationPanel bufferLocationPanel;
    private JCheckBox saveCheckBox;

    BufferCheckboxPanel(BufferLocationPanel bufferLocationPanel) {
        this.bufferLocationPanel = bufferLocationPanel;

        saveCheckBox = new JCheckBox("Buffer Files to Disk");
        add(saveCheckBox);
        saveCheckBox.addActionListener(new BufferCheckBoxActionListener());
    }

    class BufferCheckBoxActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            bufferLocationPanel.setActive(saveCheckBox.isSelected());
        }
    }

    public boolean isSelected() {
        return saveCheckBox.isSelected();
    }
}
