package kb_creator.gui.leftpanel.optionsPanel.BufferPanel;



import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;


public class BufferPanel extends JPanel {

    private BufferLocationPanel bufferLocationPanel;
    private BufferCheckboxPanel bufferCheckboxPanel;

    public BufferPanel() {
        setBorder(BorderFactory.createTitledBorder("Buffering"));

        bufferCheckboxPanel = new BufferCheckboxPanel();

        bufferLocationPanel = new BufferLocationPanel(bufferCheckboxPanel);



        add(bufferLocationPanel);
        add(bufferCheckboxPanel);
    }

    public boolean isBufferingRequested() {
        return bufferCheckboxPanel.isSelected();
    }

    public String getPath() {
        return bufferLocationPanel.getFilePath();
    }



}
