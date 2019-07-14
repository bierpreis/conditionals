package kb_creator.gui.left_panel.optionsPanel;

import javax.swing.*;
import java.awt.*;

public class SignatureOptionsPanel extends JPanel {

    private ButtonGroup signatureButtonGroup;


    public SignatureOptionsPanel() {
        setBorder(BorderFactory.createTitledBorder("Choose signature"));

        signatureButtonGroup = new ButtonGroup();

        JRadioButton abRadioButton = new JRadioButton("ab");
        JRadioButton abcRadioButton = new JRadioButton("abc");


        abRadioButton.setActionCommand("ab");

        abcRadioButton.setActionCommand("abc");

        add(abRadioButton);
        add(abcRadioButton);

        signatureButtonGroup.add(abRadioButton);
        signatureButtonGroup.add(abcRadioButton);
        abRadioButton.setSelected(true);


        setBorder(BorderFactory.createTitledBorder("signature"));

    }


    public String getOption() {
        return signatureButtonGroup.getSelection().getActionCommand();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }
}
