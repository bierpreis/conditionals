package kb_creator.gui.leftpanel.optionsPanel.BufferPanel;



import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;


public class BufferPanel extends JPanel {

    private String requestedPath = null;
    private BufferLocationPanel bufferLocationPanel;
    private BufferCheckboxPanel bufferCheckboxPanel;

    public BufferPanel() {
        setBorder(BorderFactory.createTitledBorder("Buffering"));

        bufferLocationPanel = new BufferLocationPanel();

        bufferCheckboxPanel = new BufferCheckboxPanel(bufferLocationPanel);


        add(bufferCheckboxPanel);
        add(bufferLocationPanel);
    }

    public boolean isBufferingRequested() {
        return bufferCheckboxPanel.isSelected();
    }

    public String getPath() {
        return bufferLocationPanel.getFilePath();
    }



}
