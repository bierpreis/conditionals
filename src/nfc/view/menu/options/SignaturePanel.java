package nfc.view.menu.options;

import kb_creator.model.propositional_logic.Signature.AB;
import kb_creator.model.propositional_logic.Signature.ABC;
import kb_creator.model.propositional_logic.Signature.AbstractSignature;

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

    public AbstractSignature getRequestedSignature() {
        String signature = signatureButtonGroup.getSelection().getActionCommand();

        if (signature.equals("ab"))
            return new AB();
        if (signature.equals("abc"))
            return new ABC();
        else throw new RuntimeException("No valid signature: " + signature);
    }


}
