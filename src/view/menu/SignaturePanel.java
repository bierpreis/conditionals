package view.menu;

import model.World;
import model.WorldDifference;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignaturePanel extends JPanel {

    ButtonGroup signatureButtonGroup;

    public SignaturePanel() {
        setBorder(BorderFactory.createTitledBorder("Choose Signature"));

        signatureButtonGroup = new ButtonGroup();

        JRadioButton abRadioButton = new JRadioButton("ab");
        JRadioButton abcRadioButton = new JRadioButton("abc");

        abRadioButton.addActionListener(new SignatureRadioButtonListener());
        abRadioButton.setActionCommand("ab");
        abcRadioButton.addActionListener(new SignatureRadioButtonListener());
        abcRadioButton.setActionCommand("abc");

        add(abRadioButton);
        add(abcRadioButton);

        signatureButtonGroup.add(abRadioButton);
        signatureButtonGroup.add(abcRadioButton);
        abRadioButton.setSelected(true);

        WorldDifference.setSignature(signatureButtonGroup.getSelection().getActionCommand());
        World.setSignature(signatureButtonGroup.getSelection().getActionCommand());
        World.setLettersMode(signatureButtonGroup.getSelection().getActionCommand());

        setBorder(BorderFactory.createTitledBorder("Signature"));

    }

    class SignatureRadioButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            WorldDifference.setSignature(e.getActionCommand());
            World.setSignature(e.getActionCommand());
            World.setLettersMode(e.getActionCommand());
        }
    }

    public ButtonGroup getSignatureButtonGroup() {//todo: remove?
        return signatureButtonGroup;
    }


}
