package kb_creator.gui.leftpanel.BufferPanel;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BufferPanel extends JPanel {

    private String requestedPath = null;
    private BufferLocationPanel bufferLocationPanel;
    private BufferCheckboxPanel bufferCheckboxPanel;

    public BufferPanel() {
        setBorder(BorderFactory.createTitledBorder("Buffering"));

        bufferLocationPanel = new BufferLocationPanel();

        bufferCheckboxPanel = new BufferCheckboxPanel(bufferLocationPanel);


        add(bufferCheckboxPanel);
        add(bufferLocationPanel);
    }

    public boolean isBufferingRequested() {
        return bufferCheckboxPanel.isSelected();
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
