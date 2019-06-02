package kb_creator.gui.leftpanel.BufferPanel;

import kb_creator.gui.leftpanel.KBSavePanel.KBLocationPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BufferPanel extends JPanel {
    private ButtonGroup buttonGroup;
    private String requestedPath = null;
    private KBLocationPanel KBLocationPanel;

    public BufferPanel() {
        setBorder(BorderFactory.createTitledBorder("Buffering"));


        JRadioButton offButton = new JRadioButton("off");
        add(offButton);
        offButton.setSelected(true);
        offButton.setActionCommand("off");

        JRadioButton onButton = new JRadioButton("on");
        add(onButton);
        onButton.setActionCommand("on");

        buttonGroup = new ButtonGroup();
        buttonGroup.add(onButton);
        buttonGroup.add(offButton);

        add(KBLocationPanel = new KBLocationPanel());

        //JButton chooseFolderButton = new JButton("Choose Folder");
        //add(chooseFolderButton);

    }

    public boolean isBufferingRequested() {
        if (buttonGroup.getSelection().getActionCommand().equals("off"))
            return false;
        else if (buttonGroup.getSelection().getActionCommand().equals("on") && KBLocationPanel.getFilePath() == null) {
            new NoFilePathWarning();
            return false;
        } else return true;
    }

    public String getPath() {
        return KBLocationPanel.getFilePath();
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
