package kb_creator.gui.leftpanel.KBSavePanel;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KBLocationPanel extends JPanel {
    private JButton saveButton;
    private String filePathToSave;

    public KBLocationPanel() {
        //setBorder(BorderFactory.createTitledBorder("Choose Location to save Files"));
        saveButton = new JButton("Choose Folder");
        saveButton.addActionListener(new SaveButtonListener(this));
        add(saveButton);
    }

    private class SaveButtonListener implements ActionListener {
        KBLocationPanel kbLocationPanel;

        public SaveButtonListener(KBLocationPanel kbLocationPanel) {
            this.kbLocationPanel = kbLocationPanel;
        }

        //todo: some dialog when saving requested but no location available
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showDialog(kbLocationPanel, "Choose Folder");

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
