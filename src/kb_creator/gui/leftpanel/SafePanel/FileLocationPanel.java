package kb_creator.gui.leftpanel.SafePanel;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileLocationPanel extends JPanel {
    private JButton saveButton;
    private String filePathToSave;

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

        //todo: maybe deactivate start button when saving is on and no file selected
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showDialog(fileLocationPanel, "Choose Folder");

            //avoid null pointer exception when no file gets selected
            if (fileChooser.getSelectedFile() != null)
                filePathToSave = fileChooser.getSelectedFile().getAbsolutePath();
        }


    }

    public void setActive(boolean active) {
        saveButton.setEnabled(active);
    }

    public String getFilePath() {
        return filePathToSave;
    }
}
