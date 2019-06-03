package kb_creator.gui.leftpanel.optionsPanel.KBSavePanel;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KBLocationPanel extends JPanel {
    private JButton saveButton;
    private String filePathToSave;
    private KBCheckboxPanel checkboxPanel;

    public KBLocationPanel(KBCheckboxPanel checkboxPanel) {
        saveButton = new JButton("Choose Folder");
        saveButton.addActionListener(new SaveButtonListener(this));
        add(saveButton);

        saveButton.setEnabled(true);
        this.checkboxPanel = checkboxPanel;
    }

    private class SaveButtonListener implements ActionListener {
        KBLocationPanel kbLocationPanel;

        public SaveButtonListener(KBLocationPanel kbLocationPanel) {
            this.kbLocationPanel = kbLocationPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showDialog(kbLocationPanel, "Choose Folder");

            //avoid null pointer exception when no file gets selected
            if (fileChooser.getSelectedFile() != null) {
                checkboxPanel.setActive(true);
                filePathToSave = fileChooser.getSelectedFile().getAbsolutePath() + "/kbs/";

            }

        }


    }

    public void setActive(boolean active) {
        saveButton.setEnabled(active);
    }

    public String getFilePath() {
        return filePathToSave;
    }
}
