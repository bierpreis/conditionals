package kb_creator.gui.leftpanel.optionsPanel.BufferPanel;


import javax.swing.*;
import java.awt.*;


public class BufferPanel extends JPanel {

    private BufferLocationPanel bufferLocationPanel;
    private BufferCheckboxPanel bufferCheckboxPanel;
    private JPanel descriptionPanel = new JPanel();
    private JPanel actionPanel = new JPanel();

    //todo: option to delete buffer files
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

        descriptionPanel.getComponent(0).setEnabled(enabled);

        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }
}
