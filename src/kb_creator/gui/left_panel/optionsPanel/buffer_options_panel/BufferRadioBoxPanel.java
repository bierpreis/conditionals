package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;


import kb_creator.model.buffer.BufferingType;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class BufferRadioBoxPanel extends JPanel {
    private MainBufferPanel mainBufferPanel;

    private JRadioButton simpleRamBufferButton = new JRadioButton("Simple Ram");
    private JRadioButton compressedRamBufferButton = new JRadioButton("Compressed Ram");
    private JRadioButton hddBufferButton = new JRadioButton("Harddisk");

    private ButtonGroup buttonGroup = new ButtonGroup();

    BufferRadioBoxPanel(MainBufferPanel mainBufferPanel) {
        setBorder(BorderFactory.createTitledBorder("data structure"));

        buttonGroup.add(simpleRamBufferButton);
        buttonGroup.add(compressedRamBufferButton);
        buttonGroup.add(hddBufferButton);

        compressedRamBufferButton.setSelected(true);

        add(simpleRamBufferButton);
        add(compressedRamBufferButton);
        add(hddBufferButton);

        //add(buttonGroup);

        //buttonGroup.addChangeListener(new CheckBoxActionListener());
        this.mainBufferPanel = mainBufferPanel;
    }

    public BufferingType getBufferingType() {
        if (simpleRamBufferButton.isSelected())
            return BufferingType.SIMPLE_RAM;
        if (compressedRamBufferButton.isSelected())
            return BufferingType.COMPRESSED_RAM;
        if (hddBufferButton.isSelected())
            return BufferingType.HDD;

        throw new RuntimeException("something went wrong in buffer raadio box panel!");
    }

    public void setBoxEnabled(boolean active) {
        //todo
        //saveCheckBox.setEnabled(active);
    }

    public void setBoxSelected(boolean selected) {
        //todo
        //aveCheckBox.setSelected(selected);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        //todo
        //saveCheckBox.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }

    //todo: use
    class CheckBoxActionListener implements ChangeListener {


        @Override
        public void stateChanged(ChangeEvent changeEvent) {

            if (!hddBufferButton.isSelected()) {
                mainBufferPanel.getBufferRadioBoxPanel().setBoxSelected(false);
                mainBufferPanel.getBufferSizePanel().setEnabled(false);
                mainBufferPanel.getDeleteCheckbox().setEnabled(false);
                mainBufferPanel.getFileNameLengthPanel().setEnabled(false);
            }


        }
    }
}



