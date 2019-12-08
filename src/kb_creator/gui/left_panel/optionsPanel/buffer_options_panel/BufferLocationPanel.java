package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;

import kb_creator.gui.left_panel.optionsPanel.warnings.AlreadyExistsDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class BufferLocationPanel extends JPanel {
    private JButton saveButton;
    private String filePathToSave;
    private MainBufferPanel mainBufferPanel;

    public BufferLocationPanel(MainBufferPanel mainBufferPanel) {

        saveButton = new JButton("Choose Folder");
        saveButton.addActionListener(new BufferSaveButtonListener(this));
        add(saveButton);
        this.mainBufferPanel = mainBufferPanel;


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


            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showDialog(bufferLocationPanel, "Choose Folder");

            //avoid null pointer exception when no file gets selected
            if (fileChooser.getSelectedFile() != null) {
                mainBufferPanel.getBufferSizePanel().setEnabled(true);
                mainBufferPanel.getDeleteCheckbox().setEnabled(true);
                filePathToSave = fileChooser.getSelectedFile().getAbsolutePath() + "/KBs";

                File fileToSave = new File(filePathToSave);

                if (fileToSave.exists()) {

                    new AlreadyExistsDialog(filePathToSave);
                    mainBufferPanel.getBufferSizePanel().setEnabled(false);
                    mainBufferPanel.getDeleteCheckbox().setEnabled(false);
                }

            }
            //this triggers when path is null so no file selected
            else {
                mainBufferPanel.getBufferSizePanel().setEnabled(false);
                mainBufferPanel.getDeleteCheckbox().setEnabled(false);
            }
        }


    }


    public String getFilePath() {
        return filePathToSave;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        saveButton.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }
}
