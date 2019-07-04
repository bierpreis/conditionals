package kb_creator.gui.writerpanel;


import kb_creator.model.conditionals.pair_lists.AbstractPairBuffer;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class MainBufferPanel extends JPanel {
    private JLabel statusLabel;
    private JLabel writerLabel;
    private JLabel readerLabel;


    //todo: try some left alignment?
    public MainBufferPanel() {
        setBorder(BorderFactory.createTitledBorder("Candidates Buffer"));


        Box vBox = Box.createVerticalBox();
        add(vBox);

        statusLabel = new JLabel();
        writerLabel = new JLabel();
        readerLabel = new JLabel();
        vBox.add(statusLabel);
        vBox.add(writerLabel);
        vBox.add(readerLabel);
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
