package kb_creator.gui.leftpanel.optionsPanel.BufferPanel;


import nfc.view.menu.options.OptionsPanel;

import javax.swing.*;
import java.awt.*;


public class MainBufferPanel extends JPanel {

    private BufferLocationPanel bufferLocationPanel;
    private BufferCheckboxPanel bufferCheckboxPanel;
    private JPanel descriptionPanel;
    private JPanel optionsPanel;
    private JPanel actionPanel;
    private JCheckBox deleteTempFilesCheckbox;
    private BufferSizePanel bufferSizePanel;


    public MainBufferPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Buffering"));

        descriptionPanel = new JPanel();

        bufferCheckboxPanel = new BufferCheckboxPanel();

        bufferLocationPanel = new BufferLocationPanel(this);

        actionPanel = new JPanel();

        bufferSizePanel = new BufferSizePanel();


        add(descriptionPanel);
        descriptionPanel.add(new JLabel("Buffer temp Files to Disk to save main memory"));

        actionPanel.add(bufferLocationPanel);
        actionPanel.add(add(bufferCheckboxPanel));
        add(actionPanel);


        optionsPanel = new JPanel();


        //todo: function for deleting files
        deleteTempFilesCheckbox = new JCheckBox("Delete temporary Files");
        optionsPanel.add(deleteTempFilesCheckbox);
        deleteTempFilesCheckbox.setSelected(true);


        add(optionsPanel);

        add(bufferSizePanel);

        bufferCheckboxPanel.setBoxSelected(false);
        bufferCheckboxPanel.setBoxEnabled(false);
        bufferSizePanel.setEnabled(false);
        getDeleteCheckbox().setEnabled(false);


    }

    public boolean isBufferingRequested() {
        return bufferCheckboxPanel.isSelected();
    }

    public String getPath() {
        return bufferLocationPanel.getFilePath();
    }


    @Override
    public void setEnabled(boolean enabled) {
        bufferCheckboxPanel.setEnabled(enabled);
        bufferLocationPanel.setEnabled(enabled);

        deleteTempFilesCheckbox.setEnabled(enabled);
        bufferSizePanel.setEnabled(enabled);

        descriptionPanel.getComponent(0).setEnabled(enabled);


        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }

    public int getBufferSize() {
        return bufferSizePanel.getBufferSize();
    }

    public BufferCheckboxPanel getBufferCheckboxPanel() {
        return bufferCheckboxPanel;
    }

    public BufferLocationPanel getBufferLocationPanel() {
        return bufferLocationPanel;
    }

    public BufferSizePanel getBufferSizePanel() {
        return bufferSizePanel;
    }

    public JCheckBox getDeleteCheckbox() {
        return deleteTempFilesCheckbox;
    }
}
