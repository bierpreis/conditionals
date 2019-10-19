package kb_creator.gui.left_panel.optionsPanel.Warnings;

import kb_creator.gui.left_panel.optionsPanel.Warnings.AbstractWarningDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlreadyExistsDialog extends AbstractWarningDialog {
    public AlreadyExistsDialog(String pathOfExistingFolder) {
        super();


        descriptionLabel.setText("The Folder you chosse already exits.");
        descriptionLabel2.setText("(" + pathOfExistingFolder + ")");
        descriptionLabel3.setText("Choose diffrent Folder or delete it go continue.");

        repaint();
        setVisible(true);
    }
}
