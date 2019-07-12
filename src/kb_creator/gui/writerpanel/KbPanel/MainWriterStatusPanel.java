package kb_creator.gui.writerpanel.KbPanel;

import kb_creator.gui.writerpanel.MainBufferStatusPanel;

import javax.swing.*;
import java.awt.*;


public class MainWriterStatusPanel extends JPanel {

    private KbQueuePanel kbQueuePanel;
    private MainKbWriterPanel mainKbWriterPanel;
    private MainBufferStatusPanel mainBufferStatusPanel;

    public MainWriterStatusPanel() {
        setPreferredSize(new Dimension(300, 100));
        setBorder(BorderFactory.createTitledBorder("Knowledge Base Writer"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        kbQueuePanel = new KbQueuePanel();
        add(kbQueuePanel);
        mainKbWriterPanel = new MainKbWriterPanel();
        add(mainKbWriterPanel);

        mainBufferStatusPanel = new MainBufferStatusPanel();
        add(mainBufferStatusPanel);
    }

    public KbQueuePanel getKbQueuePanel() {
        return kbQueuePanel;
    }

    public MainKbWriterPanel getMainKbWriterPanel() {
        return mainKbWriterPanel;
    }

    public MainBufferStatusPanel getCandidatesPanel() {
        return mainBufferStatusPanel;
    }
}
