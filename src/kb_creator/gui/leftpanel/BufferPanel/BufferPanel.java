package kb_creator.gui.leftpanel.BufferPanel;

import kb_creator.gui.leftpanel.KBSavePanel.KBLocationPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BufferPanel extends JPanel {
    private ButtonGroup buttonGroup;
    private String requestedPath = null;
    private BufferLocationPanel bufferLocationPanel;

    public BufferPanel() {
        setBorder(BorderFactory.createTitledBorder("Buffering"));




        add(bufferLocationPanel = new BufferLocationPanel());

        //JButton chooseFolderButton = new JButton("Choose Folder");
        //add(chooseFolderButton);

    }

    public boolean isBufferingRequested() {
        if (buttonGroup.getSelection().getActionCommand().equals("off"))
            return false;
        else if (buttonGroup.getSelection().getActionCommand().equals("on") && bufferLocationPanel.getFilePath() == null) {
            new NoFilePathWarning();
            return false;
        } else return true;
    }

    public String getPath() {
        return bufferLocationPanel.getFilePath();
    }

    private class NoFilePathWarning {
        JDialog warningDialog;

        NoFilePathWarning() {
            warningDialog = new JDialog();

            warningDialog.add(new JLabel("No Path for Buffering was chosen"));
            warningDialog.add(new JLabel("Continuing without Buffering"));


            JButton okButton = new JButton("ok");
            warningDialog.add(okButton);
            okButton.addActionListener(new OKButtonListener(this));


        }

        void dispose() {
            warningDialog.dispose();
        }
    }

    private class OKButtonListener implements ActionListener {
        NoFilePathWarning noFilePathWarning;

        OKButtonListener(NoFilePathWarning warningDialog) {
            noFilePathWarning = warningDialog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            noFilePathWarning.dispose();
        }
    }
}
