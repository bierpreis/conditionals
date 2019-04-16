package nfc.view.menu;
import javax.swing.*;

public class NumbersPanel extends JPanel {
    private ButtonGroup buttonGroup;

    public NumbersPanel() {
        buttonGroup = new ButtonGroup();

        setBorder(BorderFactory.createTitledBorder("Show Numbers"));
        JRadioButton onButton = new JRadioButton("on");
        onButton.setActionCommand("on");
        buttonGroup.add(onButton);
        JRadioButton offButton = new JRadioButton("off");
        buttonGroup.add(offButton);
        offButton.setSelected(true);
        offButton.setActionCommand("off");
        add(onButton);
        add(offButton);
    }

    public String getOption() {
        return buttonGroup.getSelection().getActionCommand();
    }
}
