package kb_creator.gui.writerPanel;

import javax.swing.*;


public class MainWriterPanel extends JPanel {
    private QueuePanel queuePanel;
    private WriterPanel writerPanel;

    public MainWriterPanel() {
        setBorder(BorderFactory.createTitledBorder("File Writer"));


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
