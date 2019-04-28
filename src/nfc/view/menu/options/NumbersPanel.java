package nfc.view.menu.options;

import javax.swing.*;

public class NumbersPanel extends JPanel {
    private JRadioButton onButton;

    public NumbersPanel() {
        ButtonGroup buttonGroup = new ButtonGroup();
        setBorder(BorderFactory.createTitledBorder("Show Numbers"));
        onButton = new JRadioButton("on");
        onButton.setActionCommand("on");
        buttonGroup.add(onButton);
        JRadioButton offButton = new JRadioButton("off");
        buttonGroup.add(offButton);
        offButton.setSelected(true);
        offButton.setActionCommand("off");
        add(onButton);
        add(offButton);
    }

    public boolean isNumbersActive() {
        return onButton.isSelected();
    }
}
