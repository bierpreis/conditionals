package kb_creator.gui.leftpanel.optionsPanel.BufferPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

//todo: inform when folder already exists and delete existing folder or abort
public class BufferLocationPanel extends JPanel {
    private JButton saveButton;
    private String filePathToSave;
    private BufferCheckboxPanel bufferCheckboxPanel;
    private JLabel descriptionLabel;

    public BufferLocationPanel(BufferCheckboxPanel bufferCheckboxPanel) {


        descriptionLabel = new JLabel("Choose non existing Folder for Saving files to use less Main Memeory");
        saveButton = new JButton("Choose Folder");
        saveButton.addActionListener(new BufferSaveButtonListener(this));
        add(saveButton);
        this.bufferCheckboxPanel = bufferCheckboxPanel;
    }

    private class BufferSaveButtonListener implements ActionListener {
        BufferLocationPanel bufferLocationPanel;

        public BufferSaveButtonListener(BufferLocationPanel bufferLocationPanel) {
            this.bufferLocationPanel = bufferLocationPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);

            //todo: create some subfolder?
/*
            File workingDirectory = new File(System.getProperty("user.dir") + "/KBs");
            System.out.println(System.getProperty("user.dir"));
            fileChooser.setCurrentDirectory(workingDirectory);
*/


            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showDialog(bufferLocationPanel, "Choose Folder");

            //avoid null pointer exception when no file gets selected
            if (fileChooser.getSelectedFile() != null) {
                bufferCheckboxPanel.setActive(true);
                bufferCheckboxPanel.setBoxSelected(true);
                filePathToSave = fileChooser.getSelectedFile().getAbsolutePath();

                //todo: if file exists, waring dialogue and dont start
                System.out.println("exists: " + fileChooser.getSelectedFile().exists());

            } else {
                bufferCheckboxPanel.setBoxSelected(false);
                bufferCheckboxPanel.setBoxEnabled(false);
            }
        }


    }


    public String getFilePath() {
        return filePathToSave;
    }

    public void setActive(boolean active) {
        saveButton.setEnabled(active);
    }
}
