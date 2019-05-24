package kb_creator.gui.leftpanel.SafePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckboxPanel extends JPanel {
    private JCheckBox checkBox;
    private FileLocationPanel fileLocationPanel;

    public CheckboxPanel(FileLocationPanel safePanel) {
        checkBox = new JCheckBox("Save KBs to File");
        add(checkBox);
        this.fileLocationPanel = safePanel;
        checkBox.addActionListener(new CheckboxListener());
        fileLocationPanel.setActive(checkBox.isSelected());

    }

    class CheckboxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fileLocationPanel.setActive(checkBox.isSelected());

        }
    }
}
