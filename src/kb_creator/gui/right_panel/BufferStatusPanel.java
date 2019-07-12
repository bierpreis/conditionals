package kb_creator.gui.right_panel;


import kb_creator.model.conditionals.buffer.AbstractPairBuffer;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class BufferStatusPanel extends JPanel {
    private JLabel statusLabel;
    private JLabel writerLabel;
    private JLabel readerLabel;

    //todo: seperate better between writer and buffer
    public BufferStatusPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createTitledBorder("Candidates Buffer"));


        Box vBox = Box.createVerticalBox();
        add(vBox);

        statusLabel = new JLabel();
        writerLabel = new JLabel();
        readerLabel = new JLabel();
        vBox.add(statusLabel);
        vBox.add(writerLabel);
        vBox.add(readerLabel);
        vBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        showStatus(AbstractPairBuffer.BufferStatus.NOT_STARTED);
        showWriterQueue(0);
        showReaderProgress(0);

    }


    public void showStatus(AbstractPairBuffer.BufferStatus status) {
        statusLabel.setText("Status: " + status.toString());
    }


    public void showWriterQueue(int alreadyFinishedNumber) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        writerLabel.setText("Writer Queue: " + formatter.format(alreadyFinishedNumber));
    }


    public void showReaderProgress(int alreadyReadNumber) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        readerLabel.setText("Read Pairs: " + formatter.format(alreadyReadNumber));
    }

}
