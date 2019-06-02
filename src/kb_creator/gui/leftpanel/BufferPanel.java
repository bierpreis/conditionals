package kb_creator.gui.leftpanel;

import javax.swing.*;

public class BufferPanel extends JPanel {
    ButtonGroup buttonGroup;

    public BufferPanel() {
        setBorder(BorderFactory.createTitledBorder("Buffering"));

        JRadioButton onButton = new JRadioButton("on");
        add(onButton);

        JRadioButton offButton = new JRadioButton("off");
        add(offButton);
        offButton.setSelected(true);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(onButton);
        buttonGroup.add(offButton);
    }

    public boolean isBufferingRequested() {
        if (buttonGroup.getSelection().getActionCommand().equals("on"))
            return true;
        else return false;
    }
}
