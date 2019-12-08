package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;


import kb_creator.model.buffer.BufferingType;

import javax.swing.*;
import java.awt.*;


public class MainBufferPanel extends JPanel {

    private BufferLocationPanel bufferLocationPanel;
    private BufferRadioBoxPanel bufferRadioBoxPanel;

    private JPanel optionsPanel;
    private JCheckBox deleteTempFilesCheckbox;

    private BufferSizePanel bufferSizePanel;
    private FileNameLengthPanel fileNameLengthPanel;


    public MainBufferPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Buffering"));


        bufferRadioBoxPanel = new BufferRadioBoxPanel(this);

        bufferLocationPanel = new BufferLocationPanel(this);



        bufferSizePanel = new BufferSizePanel();

        fileNameLengthPanel = new FileNameLengthPanel();



        add(bufferRadioBoxPanel);

        optionsPanel = new JPanel();
        optionsPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        optionsPanel.add(bufferLocationPanel);

        deleteTempFilesCheckbox = new JCheckBox("Delete temporary Files");
        optionsPanel.add(deleteTempFilesCheckbox);

        optionsPanel.add(bufferSizePanel);
        optionsPanel.add(fileNameLengthPanel);

        add(optionsPanel);


    }





    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }



    public boolean isValueValid(){
        return bufferSizePanel.isValueValid() && fileNameLengthPanel.isValueValid();
    }

    public void init(){
        deleteTempFilesCheckbox.setSelected(true);
        bufferRadioBoxPanel.setBoxSelected(false);
        bufferRadioBoxPanel.setBoxEnabled(false);
        bufferSizePanel.setEnabled(false);
        fileNameLengthPanel.setEnabled(false);
        deleteTempFilesCheckbox.setEnabled(false);
    }

    //getters

    public BufferSizePanel getBufferSizePanel() {
        return bufferSizePanel;
    }

    public JCheckBox getDeleteCheckbox() {
        return deleteTempFilesCheckbox;
    }

    public FileNameLengthPanel getFileNameLengthPanel(){
        return fileNameLengthPanel;
    }

    public int getBufferSize() {
        return bufferSizePanel.getBufferSize();
    }

    public BufferRadioBoxPanel getBufferRadioBoxPanel() {
        return bufferRadioBoxPanel;
    }

    public String getBufferFilePath() {
        return bufferLocationPanel.getFilePath();
    }

    public BufferingType getBufferingType() {
        return bufferRadioBoxPanel.getBufferingType();
    }

}
