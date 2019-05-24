package kb_creator.gui.leftpanel.SafePanel;


import javax.swing.*;
import java.io.File;

public class SafePanel extends JPanel {
    private CheckboxPanel checkboxPanel;
    private FileLocationPanel fileLocationPanel;


    public SafePanel() {
        setBorder(BorderFactory.createTitledBorder("Safe Options"));
        fileLocationPanel = new FileLocationPanel();
        checkboxPanel = new CheckboxPanel(fileLocationPanel);
        add(checkboxPanel);

        add(fileLocationPanel);
    }

    public File getFileLocation() {
        if (checkboxPanel.isSavingRequested())
            return fileLocationPanel.getFilePath();
        else return null;
    }


}
