package kb_creator.gui.writerpanel.writer_panel;

import kb_creator.gui.writerpanel.MainBufferStatusPanel;

import javax.swing.*;
import java.awt.*;


public class MainWriterStatusPanel extends JPanel {

    private KbQueuePanel kbQueuePanel;
    private KbWriterPanel kbWriterPanel;
    private MainBufferStatusPanel mainBufferStatusPanel;

    public MainWriterStatusPanel() {
        setPreferredSize(new Dimension(300, 100));
        setBorder(BorderFactory.createTitledBorder("Knowledge Base Writer"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        kbQueuePanel = new KbQueuePanel();
        add(kbQueuePanel);
        kbWriterPanel = new KbWriterPanel();
        add(kbWriterPanel);

        //todo: remove this from writer panel into own panel
        mainBufferStatusPanel = new MainBufferStatusPanel();
        add(mainBufferStatusPanel);
    }

    public KbQueuePanel getKbQueuePanel() {
        return kbQueuePanel;
    }

    public KbWriterPanel getKbWriterPanel() {
        return kbWriterPanel;
    }

    public MainBufferStatusPanel getCandidatesPanel() {
        return mainBufferStatusPanel;
    }
}
