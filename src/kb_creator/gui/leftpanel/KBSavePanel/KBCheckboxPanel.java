package kb_creator.gui.leftpanel.KBSavePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KBCheckboxPanel extends JPanel {
    private JCheckBox checkBox;
    private KBLocationPanel KBLocationPanel;

    public KBCheckboxPanel(KBLocationPanel safePanel) {
        checkBox = new JCheckBox("Save KBs to File");
        add(checkBox);
        this.KBLocationPanel = safePanel;
        checkBox.addActionListener(new CheckboxListener());
        KBLocationPanel.setActive(checkBox.isSelected());

    }

    class CheckboxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            KBLocationPanel.setActive(checkBox.isSelected());

        }
    }
}
