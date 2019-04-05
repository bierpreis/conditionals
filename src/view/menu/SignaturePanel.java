package view.menu;

import controller.GuiObserver;
import model.World;
import model.WorldDifference;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SignaturePanel extends JPanel {

    ButtonGroup signatureButtonGroup;

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public SignaturePanel(GuiObserver observer) {
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

        changes.addPropertyChangeListener(observer);

    }

    class SignatureRadioButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            changes.firePropertyChange("signature:" + signatureButtonGroup.getSelection().getActionCommand(), true, false);
        }
    }



}
