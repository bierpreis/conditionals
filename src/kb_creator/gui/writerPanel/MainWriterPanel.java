package kb_creator.gui.writerPanel;

import kb_creator.gui.candidatesPanel.MainCandidatesPanel;

import javax.swing.*;


public class MainWriterPanel extends JPanel {

    private QueuePanel queuePanel;
    private WriterPanel writerPanel;
    private MainCandidatesPanel mainCandidatesPanel;

    public MainWriterPanel() {
        setBorder(BorderFactory.createTitledBorder("Knowledge Base Writer"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        queuePanel = new QueuePanel();
        add(queuePanel);
        writerPanel = new WriterPanel();
        add(writerPanel);

        mainCandidatesPanel = new MainCandidatesPanel();
        add(mainCandidatesPanel);
    }

    public QueuePanel getQueuePanel() {
        return queuePanel;
    }

    public WriterPanel getWriterPanel() {
        return writerPanel;
    }
}
