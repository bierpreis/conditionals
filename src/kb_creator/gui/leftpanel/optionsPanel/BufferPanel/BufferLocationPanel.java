package kb_creator.gui.leftpanel.optionsPanel.BufferPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BufferLocationPanel extends JPanel {
    private JButton saveButton;
    private String filePathToSave;
    private BufferCheckboxPanel bufferCheckboxPanel;

    public BufferLocationPanel(BufferCheckboxPanel bufferCheckboxPanel) {

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
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showDialog(bufferLocationPanel, "Choose Folder");

            //avoid null pointer exception when no file gets selected
            if (fileChooser.getSelectedFile() != null) {
                bufferCheckboxPanel.setActive(true);
                filePathToSave = fileChooser.getSelectedFile().getAbsolutePath();

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
