package kb_creator.gui.leftpanel.optionsPanel.BufferPanel;


import javax.swing.*;


public class BufferPanel extends JPanel {

    private BufferLocationPanel bufferLocationPanel;
    private BufferCheckboxPanel bufferCheckboxPanel;
    JPanel descriptionPanel = new JPanel();
    JPanel actionPanel = new JPanel();


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


}
