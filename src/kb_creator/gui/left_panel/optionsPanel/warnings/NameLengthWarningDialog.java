package kb_creator.gui.left_panel.optionsPanel.warnings;


public class NameLengthWarningDialog extends AbstractWarningDialog {
    public NameLengthWarningDialog(){
        descriptionLabel.setText("Warning! Invalid input for file name length!");
        setVisible(true);
    }
}
