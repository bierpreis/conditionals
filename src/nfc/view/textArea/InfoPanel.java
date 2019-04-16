package nfc.view.textArea;

import javax.swing.*;

public class InfoPanel extends JPanel {

    private final JLabel descriptionLabel = new JLabel("empty");


    public InfoPanel() {
        add(descriptionLabel);

    }

    public void printInfo(String description) {
        descriptionLabel.setText(description);

    }


}
