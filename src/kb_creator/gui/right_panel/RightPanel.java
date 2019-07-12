package kb_creator.gui.right_panel;

import kb_creator.gui.right_panel.writer_status_panel.WriterStatusPanel;

import javax.swing.*;

public class RightPanel extends JPanel {
    private BufferStatusPanel bufferStatusPanel;
    private WriterStatusPanel writerStatusPanel;

    public RightPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder());

        writerStatusPanel = new WriterStatusPanel();
        add(writerStatusPanel);

        bufferStatusPanel = new BufferStatusPanel();
        add(bufferStatusPanel);
    }


    public BufferStatusPanel getCandidatesPanel() {
        return bufferStatusPanel;
    }

    public WriterStatusPanel getWriterStatusPanel() {
        return writerStatusPanel;
    }
}
