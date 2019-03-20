package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsPanel extends JPanel {

    private JPanel signaturePanel;
    ButtonGroup signatureButtonGroup;

    public OptionsPanel() {
        signaturePanel = new JPanel();
        signaturePanel.setBorder(BorderFactory.createTitledBorder("Choose Signature"));

        JRadioButton abRadioButton = new JRadioButton("ab");
        JRadioButton abcRadioButton = new JRadioButton("abc");

        abRadioButton.addActionListener(new SignatureRadioButtonListener());
        abRadioButton.setActionCommand("ab");
        abcRadioButton.addActionListener(new SignatureRadioButtonListener());
        abcRadioButton.setActionCommand("abc");

        add(abRadioButton);
        add(abcRadioButton);

        signatureButtonGroup = new ButtonGroup();
        signatureButtonGroup.add(abRadioButton);
        signatureButtonGroup.add(abcRadioButton);

        setBorder(BorderFactory.createTitledBorder("Signature"));

        abRadioButton.setSelected(true);

        setVisible(true);
    }

    public String getSignature() {
        return signatureButtonGroup.getSelection().getActionCommand();
    }

    class SignatureRadioButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
        }
    }
}
