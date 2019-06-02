package kb_creator.gui.leftpanel;

import groovyjarjarantlr.actions.cpp.ActionLexer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BufferPanel extends JPanel {
    ButtonGroup buttonGroup;
    private String requestedPath = null;

    public BufferPanel() {
        setBorder(BorderFactory.createTitledBorder("Buffering"));


        JRadioButton offButton = new JRadioButton("off");
        add(offButton);
        offButton.setSelected(true);

        JRadioButton onButton = new JRadioButton("on");
        add(onButton);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(onButton);
        buttonGroup.add(offButton);

        JButton chooseFolderButton = new JButton("Choose Folder");

    }

    public boolean isBufferingRequested() {
        if (buttonGroup.getSelection().getActionCommand().equals("off"))
            return false;
        else if (buttonGroup.getSelection().getActionCommand().equals("on") && requestedPath == null) {
            new NoFilePathWarning();
            return false;
        } else return true;
    }

    public String getPath() {
        return requestedPath;
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
