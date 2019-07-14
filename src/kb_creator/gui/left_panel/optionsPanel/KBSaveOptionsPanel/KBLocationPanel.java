package kb_creator.gui.left_panel.optionsPanel.KBSaveOptionsPanel;


import kb_creator.gui.left_panel.optionsPanel.BufferOptionsPanel.AlreadyExistsDialog;

import javax.swing.*;
import java.awt.*;
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
                checkboxPanel.setBoxEnabled(true);
                filePathToSave = fileChooser.getSelectedFile().getAbsolutePath() + "/kbs/";
                checkboxPanel.setBoxSelected(true);

                if (fileChooser.getSelectedFile().exists()) {

                    new AlreadyExistsDialog(filePathToSave);
                    checkboxPanel.setEnabled(false);
                    checkboxPanel.setBoxSelected(false);
                }
            }
            //deactivate if no path was selected
            else {
                checkboxPanel.setBoxSelected(false);
                checkboxPanel.setBoxEnabled(false);
                filePathToSave = null;
            }

        }
    }

    public String getFilePath() {
        return filePathToSave;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }

}