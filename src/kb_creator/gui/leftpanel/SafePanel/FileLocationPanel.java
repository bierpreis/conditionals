package kb_creator.gui.leftpanel.SafePanel;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileLocationPanel extends JPanel {
    private JButton saveButton;
    private File file;

    public FileLocationPanel() {
        //setBorder(BorderFactory.createTitledBorder("Choose Location to save Files"));
        saveButton = new JButton("Choose Folder");
        saveButton.addActionListener(new SaveButtonListener(this));
        add(saveButton);
    }

    private class SaveButtonListener implements ActionListener {
        FileLocationPanel fileLocationPanel;

        public SaveButtonListener(FileLocationPanel fileLocationPanel) {
            this.fileLocationPanel = fileLocationPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showDialog(fileLocationPanel, "Choose Folder");
            file = fileChooser.getSelectedFile();
        }



    }

    public void setActive(boolean active) {
        saveButton.setEnabled(active);
    }

    File getFilePath() {
        return file;
    }
}
