package gui;

import javax.swing.*;

public class OptionsPanel extends JPanel {

    private JTextField cardinalityField;

    public OptionsPanel() {
        cardinalityField = new JTextField("00");
        add(new JLabel("Cardinality:"));
        add(cardinalityField);
        setBorder(BorderFactory.createTitledBorder("Signature"));

        setVisible(true);
    }

    public int getCardinality() {
        String text = cardinalityField.getText();
        return Integer.parseInt(text);
    }
}
