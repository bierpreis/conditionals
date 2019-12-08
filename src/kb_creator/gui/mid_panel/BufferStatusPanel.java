package kb_creator.gui.mid_panel;


import kb_creator.model.buffer.BufferingType;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class BufferStatusPanel extends JPanel {
    private JLabel bufferTypeLabel;
    private JLabel writerLabel;
    private JLabel readerLabel;


    public BufferStatusPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createTitledBorder("Candidates Buffer"));


        Box vBox = Box.createVerticalBox();
        add(vBox);

        bufferTypeLabel = new JLabel();
        writerLabel = new JLabel();
        readerLabel = new JLabel();
        vBox.add(bufferTypeLabel);
        vBox.add(new JLabel(" "));
        vBox.add(writerLabel);
        vBox.add(readerLabel);
        vBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        showWriterQueue(0);
        showReaderBuffer(0);

        bufferTypeLabel.setText("Buffer Type: ");

    }


    public void showWriterQueue(int alreadyFinishedNumber) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        writerLabel.setText("Writer Queue: " + formatter.format(alreadyFinishedNumber));
    }

    public void showReaderBuffer(int readerBufferSize) {
        readerLabel.setText("Reader Buffer: " + readerBufferSize);
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);

        bufferTypeLabel.setEnabled(enabled);
        readerLabel.setEnabled(enabled);
        writerLabel.setEnabled(enabled);
    }

    public void showType(BufferingType bufferingType) {
        bufferTypeLabel.setText("Buffer Type: " + bufferingType.toString());
    }

}
