package kb_creator.gui.left_panel.optionsPanel.warnings;


public class AlreadyExistsDialog extends AbstractWarningDialog {
    public AlreadyExistsDialog(String pathOfExistingFolder) {
        super();

        descriptionLabel.setText("The Folder you choose already exits.");
        descriptionLabel2.setText("(" + pathOfExistingFolder + ")");
        descriptionLabel3.setText("Choose other Folder or delete to continue.");

        repaint();
        setVisible(true);
    }
}
