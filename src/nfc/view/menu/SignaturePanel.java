package nfc.view.menu;

import javax.swing.*;

public class SignaturePanel extends JPanel {

    private ButtonGroup signatureButtonGroup;

    public SignaturePanel() {
        setBorder(BorderFactory.createTitledBorder("Choose Signature"));

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


        setBorder(BorderFactory.createTitledBorder("Signature"));

    }

    public String getRequestedSignature() {
        return signatureButtonGroup.getSelection().getActionCommand();
    }


}
