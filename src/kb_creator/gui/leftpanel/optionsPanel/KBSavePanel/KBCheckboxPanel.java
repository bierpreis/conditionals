package kb_creator.gui.leftpanel.optionsPanel.KBSavePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KBCheckboxPanel extends JPanel {
    private JCheckBox checkBox;
    private MainKbSafePanel mainKbSafePanel;

    public KBCheckboxPanel(MainKbSafePanel mainKbSafePanel) {
        checkBox = new JCheckBox("Save KBs to File");
        add(checkBox);
        this.mainKbSafePanel = mainKbSafePanel;
        checkBox.addActionListener(new CheckboxListener());
        checkBox.setEnabled(false);
    }

    class CheckboxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainKbSafePanel.setButtonActive(checkBox.isSelected());


        }
    }

    public void setActive(boolean active) {
        checkBox.setEnabled(active);
    }
}
