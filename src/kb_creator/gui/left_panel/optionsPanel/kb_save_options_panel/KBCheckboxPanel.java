package kb_creator.gui.left_panel.optionsPanel.kb_save_options_panel;

import javax.swing.*;
import java.awt.*;
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

    public void setBoxEnabled(boolean active) {
        checkBox.setEnabled(active);
    }

    public void setBoxSelected(boolean selected) {
        checkBox.setSelected(selected);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
setBoxEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }
}
