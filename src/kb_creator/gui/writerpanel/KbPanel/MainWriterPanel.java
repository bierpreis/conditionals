package kb_creator.gui.writerpanel.KbPanel;

import kb_creator.gui.writerpanel.MainBufferPanel;

import javax.swing.*;


public class MainWriterPanel extends JPanel {

    private KbQueuePanel kbQueuePanel;
    private MainKbWriterPanel mainKbWriterPanel;
    private MainBufferPanel mainBufferPanel;

    public MainWriterPanel() {
        setBorder(BorderFactory.createTitledBorder("Knowledge Base Writer"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        kbQueuePanel = new KbQueuePanel();
        add(kbQueuePanel);
        mainKbWriterPanel = new MainKbWriterPanel();
        add(mainKbWriterPanel);

        mainBufferPanel = new MainBufferPanel();
        add(mainBufferPanel);
    }

    public KbQueuePanel getKbQueuePanel() {
        return kbQueuePanel;
    }

    public MainKbWriterPanel getMainKbWriterPanel() {
        return mainKbWriterPanel;
    }

    public MainBufferPanel getCandidatesPanel() {
        return mainBufferPanel;
    }
}
