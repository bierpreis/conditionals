package kb_creator.gui.writerpanel;


import kb_creator.model.conditionals.pair_lists.AbstractPairBuffer;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class MainCandidatesPanel extends JPanel {
    private JLabel statusLabel;
    private JLabel writerLabel;
    private JLabel readerLabel;

    public MainCandidatesPanel() {
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

    //todo: useless. almost always shows reading as status. is this correct? if yes, remove
    public void showStatus(AbstractPairBuffer.BufferStatus status) {
        statusLabel.setText("Status: " + status.toString());
    }


    //todo: this is useless. at least write "writer queue" or remove completely.
    public void showWriterQueue(int alreadyFinishedNumber) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        writerLabel.setText("Queue: " + formatter.format(alreadyFinishedNumber));
    }


    //todo: this is some useless information with parallel buffer. ony useful with simple buffer? what does it mean. does it make sense? write it in description or delete info
    public void showReaderProgress(int alreadyReadNumber) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        readerLabel.setText("Read KBs: " + formatter.format(alreadyReadNumber));
    }

}
