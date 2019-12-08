package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;


import kb_creator.model.buffer.BufferingType;

import javax.swing.*;
import java.awt.*;


public class MainBufferPanel extends JPanel {

    private BufferLocationPanel bufferLocationPanel;
    private BufferCheckboxPanel bufferCheckboxPanel;
    private JPanel descriptionPanel;
    private JPanel optionsPanel;
    private JCheckBox deleteTempFilesCheckbox;

    private BufferSizePanel bufferSizePanel;
    private FileNameLengthPanel fileNameLengthPanel;


    public MainBufferPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Buffering"));

        descriptionPanel = new JPanel();

        bufferCheckboxPanel = new BufferCheckboxPanel(this);

        bufferLocationPanel = new BufferLocationPanel(this);



        bufferSizePanel = new BufferSizePanel();

        fileNameLengthPanel = new FileNameLengthPanel();
        
        add(descriptionPanel);
        descriptionPanel.add(new JLabel("Buffer temp Files to Disk to save Main Memory"));


        add(bufferCheckboxPanel);

        optionsPanel = new JPanel();
        optionsPanel.add(bufferLocationPanel);

        deleteTempFilesCheckbox = new JCheckBox("Delete temporary Files");
        optionsPanel.add(deleteTempFilesCheckbox);


        add(optionsPanel);
        add(bufferSizePanel);
        add(fileNameLengthPanel);


    }





    @Override
    public void setEnabled(boolean enabled) {
        bufferCheckboxPanel.setEnabled(enabled);
        bufferLocationPanel.setEnabled(enabled);

        deleteTempFilesCheckbox.setEnabled(enabled);
        bufferSizePanel.setEnabled(enabled);
        fileNameLengthPanel.setEnabled(enabled);

        descriptionPanel.getComponent(0).setEnabled(enabled);

        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }



    public boolean isValueValid(){
        return bufferSizePanel.isValueValid() && fileNameLengthPanel.isValueValid();
    }

    public void init(){
        deleteTempFilesCheckbox.setSelected(true);
        bufferCheckboxPanel.setBoxSelected(false);
        bufferCheckboxPanel.setBoxEnabled(false);
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

    public BufferCheckboxPanel getBufferCheckboxPanel() {
        return bufferCheckboxPanel;
    }

    public String getBufferFilePath() {
        return bufferLocationPanel.getFilePath();
    }

    public BufferingType getBufferingType() {
        return bufferCheckboxPanel.getBufferingType();
    }

}
