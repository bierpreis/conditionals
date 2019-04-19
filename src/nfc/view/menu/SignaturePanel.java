package nfc.view.menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

public class SignaturePanel extends JPanel {

    private ButtonGroup signatureButtonGroup;
    private JRadioButton abRadioButton;

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public SignaturePanel() {
        setBorder(BorderFactory.createTitledBorder("Choose Signature"));

        signatureButtonGroup = new ButtonGroup();

        abRadioButton = new JRadioButton("ab");
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


        setBorder(BorderFactory.createTitledBorder("Signature"));

    }

    //todo: what is this
    class SignatureRadioButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            changes.firePropertyChange("signature:" + signatureButtonGroup.getSelection().getActionCommand(), true, false);
        }
    }

    //todo: rename
    public boolean getOption() {
        return abRadioButton.isSelected();
    }


}
