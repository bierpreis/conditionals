package kb_creator.gui.right_panel;


import kb_creator.model.buffer.AbstractPairBuffer;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class BufferStatusPanel extends JPanel {
    private JLabel statusLabel;
    private JLabel writerLabel;
    private JLabel readerLabel;

    public BufferStatusPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createTitledBorder("Candidates Buffer"));


        Box vBox = Box.createVerticalBox();
        add(vBox);

        statusLabel = new JLabel();
        writerLabel = new JLabel();
        readerLabel = new JLabel();
        vBox.add(statusLabel);
        vBox.add(new JLabel(" "));
        vBox.add(writerLabel);
        vBox.add(readerLabel);
        vBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        showStatus(AbstractPairBuffer.BufferStatus.NOT_STARTED);
        showWriterQueue(0);
        showReaderBuffer(0);

    }

    //todo: CreatorStatus is wrong!
    public void showStatus(AbstractPairBuffer.BufferStatus status) {
        statusLabel.setText("Status: " + status.toString());
    }


    public void showWriterQueue(int alreadyFinishedNumber) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        writerLabel.setText("Writer Queue: " + formatter.format(alreadyFinishedNumber));
    }

    public void showReaderBuffer(int readerBufferSize) {
        readerLabel.setText("Reader Buffer: " + readerBufferSize);
    }


}
