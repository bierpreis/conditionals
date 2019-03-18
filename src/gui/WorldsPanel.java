package gui;

import javax.swing.*;

public class WorldsPanel extends JPanel {

    public WorldsPanel() {
        add(new JLabel("Cardinality:"));
        add(new JTextField("00"));
        setBorder(BorderFactory.createTitledBorder("Signature"));

        setVisible(true);
    }
}
