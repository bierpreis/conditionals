package kb_creator.gui.mid_panel;

import kb_creator.gui.left_panel.ActionPanel;
import kb_creator.gui.mid_panel.creator_panel.MainCreatorPanel;

import javax.swing.*;
import java.awt.*;

public class MidPanel extends JPanel {

    private MainCreatorPanel mainCreatorPanel;

    private BufferStatusPanel bufferStatusPanel;
    private WriterStatusPanel writerStatusPanel;
    private MemoryPanel memoryPanel;

    public MidPanel(ActionPanel actionPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder());

        mainCreatorPanel = new MainCreatorPanel(actionPanel);
        add(mainCreatorPanel);

        writerStatusPanel = new WriterStatusPanel();
        add(writerStatusPanel);

        bufferStatusPanel = new BufferStatusPanel();
        add(bufferStatusPanel);

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
