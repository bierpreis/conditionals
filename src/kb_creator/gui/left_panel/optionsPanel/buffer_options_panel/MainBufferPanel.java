package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;


import kb_creator.model.buffer.BufferingType;

import javax.swing.*;
import java.awt.*;


public class MainBufferPanel extends JPanel {


    private BufferRadioBoxPanel bufferRadioBoxPanel;

    private BufferOptionsPanel bufferOptionsPanel;


    public MainBufferPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Buffering"));


        bufferRadioBoxPanel = new BufferRadioBoxPanel(this);

        bufferOptionsPanel = new BufferOptionsPanel(this);


        add(bufferRadioBoxPanel);
        add(bufferOptionsPanel);


    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }


    public boolean isValueValid() {
        return bufferOptionsPanel.isValueValid();
    }


    //getters

    public BufferSizePanel getBufferSizePanel() {
        return bufferOptionsPanel.getBufferSizePanel();
    }

    public JCheckBox getDeleteCheckbox() {
        return bufferOptionsPanel.getDeleteTempFilesCheckBox();
    }


    public int getBufferSize() {
        return bufferOptionsPanel.getBufferSizePanel().getBufferSize();
    }

    public BufferRadioBoxPanel getBufferRadioBoxPanel() {
        return bufferRadioBoxPanel;
    }

    public String getBufferFilePath() {
        return bufferOptionsPanel.getBufferLocationPanel().getFilePath();
    }

    public BufferingType getBufferingType() {
        return bufferRadioBoxPanel.getBufferingType();
    }

    public void init() {
        bufferOptionsPanel.initValues();
    }

    public BufferOptionsPanel getBufferOptionsPanel() {
        return bufferOptionsPanel;
    }
}
