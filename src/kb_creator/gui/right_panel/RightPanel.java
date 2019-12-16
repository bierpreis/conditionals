package kb_creator.gui.right_panel;

import kb_creator.gui.left_panel.ActionPanel;
import kb_creator.gui.right_panel.creator_panel.MainCreatorPanel;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {

    private MainCreatorPanel mainCreatorPanel;

    private BufferStatusPanel bufferStatusPanel;
    private WriterStatusPanel writerStatusPanel;
    private MemoryPanel memoryPanel;

    public RightPanel(ActionPanel actionPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder());

        mainCreatorPanel = new MainCreatorPanel(actionPanel);
        add(mainCreatorPanel);


        bufferStatusPanel = new BufferStatusPanel();
        add(bufferStatusPanel);

        writerStatusPanel = new WriterStatusPanel();
        add(writerStatusPanel);

        memoryPanel = new MemoryPanel();
        add(memoryPanel);
    }

    public MainCreatorPanel getCreatorPanel() {
        return mainCreatorPanel;
    }


    public BufferStatusPanel getBufferStatusPanel() {
        return bufferStatusPanel;
    }

    public WriterStatusPanel getWriterStatusPanel() {
        return writerStatusPanel;
    }

    public MemoryPanel getMemoryPanel() {
        return memoryPanel;
    }

    public void setActive(boolean active) {

        //avoid iterating without use
       if (active != this.isEnabled()) {
            super.setEnabled(active);
            for (Component component : getComponents())
                component.setEnabled(active);
        }
    }




}
