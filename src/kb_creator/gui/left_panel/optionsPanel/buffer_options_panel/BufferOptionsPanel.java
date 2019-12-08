package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;

import javax.swing.*;

public class BufferOptionsPanel extends JPanel {

    private BufferLocationPanel bufferLocationPanel;

    private JCheckBox deleteTempFilesCheckbox;

    private BufferSizePanel bufferSizePanel;
    private FileNameLengthPanel fileNameLengthPanel;

    public BufferOptionsPanel(MainBufferPanel mainBufferPanel) {
        bufferLocationPanel = new BufferLocationPanel(mainBufferPanel);


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Hdd buffer options"));
        add(bufferLocationPanel);

        bufferSizePanel = new BufferSizePanel();

        fileNameLengthPanel = new FileNameLengthPanel();


        deleteTempFilesCheckbox = new JCheckBox("Delete temporary Files");
        add(deleteTempFilesCheckbox);

        add(bufferSizePanel);
        add(fileNameLengthPanel);

        initValues();

    }


    public void initValues() {
        deleteTempFilesCheckbox.setSelected(true);

        bufferLocationPanel.setEnabled(false);
        bufferSizePanel.setEnabled(false);
        fileNameLengthPanel.setEnabled(false);
        deleteTempFilesCheckbox.setEnabled(false);
    }


    public boolean isValueValid() {
        return bufferSizePanel.isValueValid() && fileNameLengthPanel.isValueValid();
    }

    public BufferSizePanel getBufferSizePanel() {
        return bufferSizePanel;
    }

    public FileNameLengthPanel getFileNameLengthPanel() {
        return fileNameLengthPanel;
    }

    public BufferLocationPanel getBufferLocationPanel() {
        return bufferLocationPanel;
    }

    public JCheckBox getDeleteTempFilesCheckBox() {
        return deleteTempFilesCheckbox;
    }

}
