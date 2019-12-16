package kb_creator.gui.left_panel.optionsPanel.warnings;


public class NoBufferPathDialog extends AbstractWarningDialog {
    public NoBufferPathDialog() {
        super();

        descriptionLabel.setText("No Path for Hdd Buffer selected.");
        descriptionLabel3.setText("Choose valid Folder or use RAM for buffering.");

        repaint();
        setVisible(true);
    }
}
