package gui.textArea;

import Logic.DataContainer;

import javax.swing.*;

public class CondPanel extends JPanel {
    private CondTextField condTextField;
    private JScrollPane scrollPane;

    private String currentDescription = "empty";

    private String worldsDescriptionLabel = "Number of Worlds: ";
    private String conditionalDescriptionLabel = "Number of Conditionals: ";
    private String equivClassesDescriptionLabel = "Number of Classes: ";

    private JPanel infoPanel;
    private JLabel numberOfConditionalsLabel;

    public CondPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        condTextField = new CondTextField();
        scrollPane = new JScrollPane(condTextField);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        infoPanel = new InfoPanel(this);

        numberOfConditionalsLabel = new JLabel("0");
        add(infoPanel);

    }

    public void printWorlds() {
        condTextField.setText("");
        int numberOfWorlds = condTextField.printWorlds();

        currentDescription = worldsDescriptionLabel + numberOfWorlds;
        revalidate();
        repaint();
    }

    public void printConditionals() {
        condTextField.setText("");
        condTextField.printConditionals();
        numberOfConditionalsLabel.setText(Integer.toString(DataContainer.getConditionalSet().size()));
        scrollPane.setViewportView(condTextField);
        revalidate();
        repaint();
    }

    public void printCnfc() {
        condTextField.setText("");
        condTextField.printCnfc();
    }

    public String getDescription() {
        return currentDescription;
    }
}
