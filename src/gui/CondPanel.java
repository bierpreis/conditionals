package gui;

import javax.swing.*;
import java.awt.*;

public class CondPanel extends JPanel {
    private CondTextField condTextField;
    JScrollPane scrollPane;

    public CondPanel() {
        setLayout(new BorderLayout());
        condTextField = new CondTextField();
        scrollPane = new JScrollPane(condTextField);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

    }

    public void printConditionals() {
        condTextField.setText("");
        condTextField.printConditionals();
        //scrollPane.setViewportView(condTextField);
        revalidate();
        repaint();
    }
}
