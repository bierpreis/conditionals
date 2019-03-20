package gui;

import Logic.DataContainer;

import javax.swing.*;

public class CondPanel extends JPanel {
    private CondTextField condTextField;
    private JScrollPane scrollPane;

    private JPanel descriptionPanel;
    private JLabel numberOfConditionalsLabel;

    public CondPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        condTextField = new CondTextField();
        scrollPane = new JScrollPane(condTextField);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        descriptionPanel = new JPanel();
        descriptionPanel.add(new JLabel("Number of Conditionals:"));

        numberOfConditionalsLabel = new JLabel("0");
        descriptionPanel.add(numberOfConditionalsLabel);
        add(descriptionPanel);

    }

    public void printWorlds() {
        condTextField.setText("");
        condTextField.printWorlds();

        revalidate();
        repaint();
    }

    public void printConditionals() {
        //condTextField.setText("");
        condTextField.printConditionals();
        numberOfConditionalsLabel.setText(Integer.toString(DataContainer.getConditionalSet().size()));
        scrollPane.setViewportView(condTextField);
        revalidate();
        repaint();
    }
}
