package gui;


import javax.swing.*;

public class CondPanel extends JPanel {
    CondTextField condTextField;

    public CondPanel() {

        condTextField = new CondTextField();
        JScrollPane scrollPane = new JScrollPane(condTextField);
        add(scrollPane);

    }

    public void printConditionals() {
        condTextField.setText("");
        condTextField.printConditionals();
        revalidate();
        repaint();
    }
}
