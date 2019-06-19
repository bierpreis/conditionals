package kb_creator.gui.writerpanel.KbPanel;

import kb_creator.gui.writerpanel.MainCandidatesPanel;

import javax.swing.*;


public class KbWriterPanel extends JPanel {

    private KbQueuePanel kbQueuePanel;
    private MainKbWriterPanel mainKbWriterPanel;
    private MainCandidatesPanel mainCandidatesPanel;

    public KbWriterPanel() {
        setBorder(BorderFactory.createTitledBorder("Knowledge Base Writer"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        kbQueuePanel = new KbQueuePanel();
        add(kbQueuePanel);
        mainKbWriterPanel = new MainKbWriterPanel();
        add(mainKbWriterPanel);

        mainCandidatesPanel = new MainCandidatesPanel();
        add(mainCandidatesPanel);
    }

    public KbQueuePanel getKbQueuePanel() {
        return kbQueuePanel;
    }

    public MainKbWriterPanel getMainKbWriterPanel() {
        return mainKbWriterPanel;
    }

    public MainCandidatesPanel getCandidatesPanel() {
        return mainCandidatesPanel;
    }
}
