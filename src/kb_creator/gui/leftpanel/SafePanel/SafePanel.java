package kb_creator.gui.leftpanel.SafePanel;


import javax.swing.*;
import java.io.File;

public class SafePanel extends JPanel {
    private CheckboxPanel checkboxPanel;
    private FileLocationPanel fileLocationPanel;

    public SafePanel() {
        setBorder(BorderFactory.createTitledBorder("Safe Options"));
        checkboxPanel = new CheckboxPanel();
        add(checkboxPanel);
        fileLocationPanel = new FileLocationPanel();
        add(fileLocationPanel);
    }

    public File getFileLocation() {
        if (checkboxPanel.isSavingRequested())
            return fileLocationPanel.getFilePath();
        else return null;
    }
}
