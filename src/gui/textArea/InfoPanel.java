package gui.textArea;

import javax.swing.*;

public class InfoPanel extends JPanel {
    private CondPanel condPanel;

    private JLabel descriptionLabel = new JLabel("empty");


    public InfoPanel(CondPanel condPanel) {
        this.condPanel = condPanel;
        add(descriptionLabel);

    }

    public void printInfo() {
        descriptionLabel.setText(condPanel.getDescription());

    }


}
