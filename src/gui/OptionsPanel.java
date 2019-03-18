package gui;

import javax.swing.*;

public class OptionsPanel extends JPanel {

    public OptionsPanel() {
        add(new JLabel("Cardinality:"));
        add(new JTextField("00"));
        setBorder(BorderFactory.createTitledBorder("Signature"));

        setVisible(true);
    }
}
