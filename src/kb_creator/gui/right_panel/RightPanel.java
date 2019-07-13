package kb_creator.gui.right_panel;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    private BufferStatusPanel bufferStatusPanel;
    private WriterStatusPanel writerStatusPanel;

    public RightPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(300, 200));

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
