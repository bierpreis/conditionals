package kb_creator.gui.left_panel.optionsPanel.kb_save_options_panel;

import kb_creator.gui.left_panel.optionsPanel.warnings.AlreadyExistsDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class KBLocationPanel extends JPanel {
    private JButton saveButton;
    private String filePathToSave;


    public KBLocationPanel(MainKbSafePanel mainKbSafePanel) {
        saveButton = new JButton("Choose Folder");
        saveButton.addActionListener(new SaveButtonListener(mainKbSafePanel));
        add(saveButton);

        saveButton.setEnabled(true);
    }


    public String getFilePath() {

        //this complicated stuff resets filepath to null so it can be only transferred once to avoid problems with writer.
        //not nice but no time to fix..
        //at least it works
        String filePathToReturn = null;
        if (filePathToSave != null)
            filePathToReturn = filePathToSave;

        filePathToSave = null;

        return filePathToReturn;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }

    public void init() {
        saveButton.setEnabled(true);
    }


    private class SaveButtonListener implements ActionListener {
        private MainKbSafePanel mainKbSafePanel;

        public SaveButtonListener(MainKbSafePanel mainKbSafePanel) {
            this.mainKbSafePanel = mainKbSafePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showDialog(mainKbSafePanel.getKbLocationPanel(), "Choose Folder");

            //avoid null pointer exception when no file gets selected
            if (fileChooser.getSelectedFile() != null) {
                mainKbSafePanel.getKbCheckboxPanel().setBoxEnabled(true);
                filePathToSave = fileChooser.getSelectedFile().getAbsolutePath() + "/KBs/";
                File fileToSave = new File(filePathToSave);
                mainKbSafePanel.getKbCheckboxPanel().setBoxSelected(true);
                mainKbSafePanel.getKbLocationPanel().setEnabled(true);
                mainKbSafePanel.getKbLocationPanel().setEnabled(true);
                mainKbSafePanel.getNameLengthPanel().setEnabled(true);
                mainKbSafePanel.getKbNumberPanel().setEnabled(true);

                if (fileToSave.exists()) {
                    new AlreadyExistsDialog(filePathToSave);
                    filePathToSave = null;
                    mainKbSafePanel.getKbCheckboxPanel().setEnabled(false);
                    mainKbSafePanel.getKbCheckboxPanel().setBoxSelected(false);
                    mainKbSafePanel.getKbLocationPanel().setEnabled(false);
                    mainKbSafePanel.getNameLengthPanel().setEnabled(false);
                    mainKbSafePanel.getKbNumberPanel().setEnabled(false);
                }
            }
            //deactivate if no path was selected
            else {
                mainKbSafePanel.getKbCheckboxPanel().setBoxSelected(false);
                mainKbSafePanel.getKbCheckboxPanel().setBoxEnabled(false);
                filePathToSave = null;
                mainKbSafePanel.getNameLengthPanel().setEnabled(false);
                mainKbSafePanel.getKbNumberPanel().setEnabled(false);

            }

        }
    }

}
