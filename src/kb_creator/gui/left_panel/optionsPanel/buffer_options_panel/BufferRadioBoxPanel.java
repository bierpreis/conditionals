package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;


import kb_creator.model.buffer.BufferingType;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class BufferRadioBoxPanel extends JPanel {
    private MainBufferPanel mainBufferPanel;

    private JRadioButton simpleRamBufferButton = new JRadioButton("Simple Ram");
    private JRadioButton compressedRamBufferButton = new JRadioButton("Compressed Ram");
    private JRadioButton hddBufferButton = new JRadioButton("Harddisk");

    private ButtonGroup buttonGroup = new ButtonGroup();

    BufferRadioBoxPanel(MainBufferPanel mainBufferPanel) {
        setBorder(BorderFactory.createTitledBorder("Data Structure"));

        buttonGroup.add(simpleRamBufferButton);
        buttonGroup.add(compressedRamBufferButton);
        buttonGroup.add(hddBufferButton);

        hddBufferButton.addItemListener(new HddButtonListener());

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


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }


    class HddButtonListener implements ItemListener {


        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            mainBufferPanel.getBufferOptionsPanel().getBufferLocationPanel().setEnabled(hddBufferButton.isSelected());
            mainBufferPanel.getBufferOptionsPanel().resetWarningColor();
        }
    }
}



