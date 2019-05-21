package kb_creator.gui.leftpanel;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileLocationPanel extends JPanel {
    private JButton saveButton;

    public FileLocationPanel() {
        setBorder(BorderFactory.createTitledBorder("Choose Location to save Files"));
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
            fileChooser.showDialog(fileLocationPanel, "Choose Folder");
        }

    }
}
