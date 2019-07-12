package kb_creator.gui.right_panel.writer_status_panel;

import kb_creator.gui.right_panel.BufferStatusPanel;

import javax.swing.*;
import java.awt.*;


public class WriterStatusPanel extends JPanel {

    private KbQueuePanel kbQueuePanel;
    private KbWriterPanel kbWriterPanel;


    public WriterStatusPanel() {
        setPreferredSize(new Dimension(300, 100));
        setBorder(BorderFactory.createTitledBorder("Knowledge Base Writer"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        kbQueuePanel = new KbQueuePanel();
        add(kbQueuePanel);
        kbWriterPanel = new KbWriterPanel();
        add(kbWriterPanel);

        //todo: remove this from writer panel into own panel

    }

    public KbQueuePanel getKbQueuePanel() {
        return kbQueuePanel;
    }

    public KbWriterPanel getKbWriterPanel() {
        return kbWriterPanel;
    }


}
