package kb_creator.gui.leftpanel.optionsPanel.BufferPanel;


import javax.swing.*;
import java.awt.*;


public class BufferPanel extends JPanel {

    private BufferLocationPanel bufferLocationPanel;
    private BufferCheckboxPanel bufferCheckboxPanel;
    private JPanel descriptionPanel = new JPanel();
    private JPanel optionsPanel;
    private JPanel actionPanel = new JPanel();
    private JCheckBox deleteTempFilesCheckbox;

    private JTextField bufferSizeField;

    //todo: add option to chose buffer file size
    public BufferPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Buffering"));

        bufferCheckboxPanel = new BufferCheckboxPanel();

        bufferLocationPanel = new BufferLocationPanel(bufferCheckboxPanel);


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

        //todo: some listener for this field which pops some warning if no valid input
        bufferSizeField = new JTextField("200");
        optionsPanel.add(bufferSizeField);

        add(optionsPanel);


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
        bufferSizeField.setEnabled(enabled);

        descriptionPanel.getComponent(0).setEnabled(enabled);


        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }

    public int getBufferSize() {
        return Integer.parseInt(bufferSizeField.getText());
    }
}
