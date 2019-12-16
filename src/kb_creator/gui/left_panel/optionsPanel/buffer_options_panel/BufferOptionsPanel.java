package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;

import javax.swing.*;

public class BufferOptionsPanel extends JPanel {

    private BufferLocationPanel bufferLocationPanel;

    private JCheckBox deleteTempFilesCheckbox;

    private BufferSizePanel bufferSizePanel;

    public BufferOptionsPanel(MainBufferPanel mainBufferPanel) {
        bufferLocationPanel = new BufferLocationPanel(mainBufferPanel);


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Hdd Buffer Options"));
        add(bufferLocationPanel);

        bufferSizePanel = new BufferSizePanel();


        deleteTempFilesCheckbox = new JCheckBox("Delete temporary Files");
        add(deleteTempFilesCheckbox);

        add(bufferSizePanel);
        initValues();

    }


    public void initValues() {
        deleteTempFilesCheckbox.setSelected(true);

        bufferLocationPanel.setEnabled(false);
        bufferSizePanel.setEnabled(false);
        deleteTempFilesCheckbox.setEnabled(false);
    }


    public boolean isValueValid() {
        return bufferSizePanel.isValueValid() && (bufferLocationPanel.isValueValid());
    }

    public BufferSizePanel getBufferSizePanel() {
        return bufferSizePanel;
    }

    //
    public BufferLocationPanel getBufferLocationPanel() {
        return bufferLocationPanel;
    }

    public JCheckBox getDeleteTempFilesCheckBox() {
        return deleteTempFilesCheckbox;
    }

    public void resetWarningColor(){
        bufferLocationPanel.resetWarningColor();
    }


}
