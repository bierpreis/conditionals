package gui.textArea;

import Logic.DataContainer;

import javax.swing.*;

public class CondPanel extends JPanel {
    private final CondTextField condTextField;
    private final JScrollPane scrollPane;

    private String currentDescription = "empty";

    private final String worldsDescriptionLabel = "Number of Worlds: ";
    private final String conditionalDescriptionLabel = "Number of Conditionals: ";
    private final String equivClassesDescriptionLabel = "Number of Classes: ";

    private final InfoPanel infoPanel;
    private final JLabel numberOfConditionalsLabel;

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
        infoPanel.printInfo();

    }

    public void printConditionals() {
        condTextField.setText("");


        numberOfConditionalsLabel.setText(Integer.toString(DataContainer.getConditionalSet().size()));
        scrollPane.setViewportView(condTextField);

        int numberOfConditionals = condTextField.printConditionals();
        currentDescription = conditionalDescriptionLabel + numberOfConditionals;
        infoPanel.printInfo();

    }

    public void printCnfc() {
        condTextField.setText("");

        //todo: rename next method call
        int numberOfClasses = condTextField.printCnfc();
        currentDescription = equivClassesDescriptionLabel + numberOfClasses;
        infoPanel.printInfo();
    }

    public String getDescription() {
        return currentDescription;
    }
}
