package kb_creator.gui.leftpanel.optionsPanel.BufferPanel;

import javax.swing.*;

public class AlreadyExistsDialog {
    AlreadyExistsDialog(String pathOfExistingFolder){
        JDialog warningDialog = new JDialog();
        warningDialog.add(new JLabel("The Folder you chosse already exits."));
        warningDialog.add(new JLabel("Choose diffrent Folder or delete it go continue"));
        warningDialog.pack();
        warningDialog.setVisible(true);
    }
}
