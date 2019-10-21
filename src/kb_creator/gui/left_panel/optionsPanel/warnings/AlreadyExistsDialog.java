package kb_creator.gui.left_panel.optionsPanel.warnings;


public class AlreadyExistsDialog extends AbstractWarningDialog {
    public AlreadyExistsDialog(String pathOfExistingFolder) {
        super();

        descriptionLabel.setText("The Folder you choose already exits.");
        descriptionLabel2.setText("(" + pathOfExistingFolder + ")");
        descriptionLabel3.setText("Choose different Folder or delete it go continue.");

        repaint();
        setVisible(true);
    }
}
