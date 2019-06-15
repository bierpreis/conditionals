package kb_creator.gui.writerPanel;

import javax.swing.*;


public class MainWriterPanel extends JPanel {

    private QueuePanel queuePanel;
    private WriterPanel writerPanel;

    public MainWriterPanel() {
        setBorder(BorderFactory.createTitledBorder("Knowledge Base Writer"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        queuePanel = new QueuePanel();
        add(queuePanel);
        writerPanel = new WriterPanel();
        add(writerPanel);
    }

    public QueuePanel getQueuePanel() {
        return queuePanel;
    }

    public WriterPanel getWriterPanel() {
        return writerPanel;
    }
}
