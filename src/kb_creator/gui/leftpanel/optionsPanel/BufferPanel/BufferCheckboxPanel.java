package kb_creator.gui.leftpanel.optionsPanel.BufferPanel;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BufferCheckboxPanel extends JPanel {
    private JCheckBox saveCheckBox;

    BufferCheckboxPanel() {


        saveCheckBox = new JCheckBox("Buffer Files to Disk");
        add(saveCheckBox);
        saveCheckBox.addActionListener(new BufferCheckBoxActionListener());

        saveCheckBox.setEnabled(false);
    }

    class BufferCheckBoxActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //todo: delete or do sth?
            ;
        }
    }

    public boolean isSelected() {
        return saveCheckBox.isSelected();
    }

    public void setActive(boolean active) {
        saveCheckBox.setEnabled(active);
    }
}
