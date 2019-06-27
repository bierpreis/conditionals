package kb_creator.gui.writerpanel;


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
        showStatus(AbstractBuffer.BufferStatus.NOT_STARTED);
        showWriterQueue(0);
        showReaderProgress(0);

    }

    public void showStatus(AbstractBuffer.BufferStatus status) {
        statusLabel.setText("Status: " + status.toString());
    }

    public void showWriterQueue(int alreadyFinishedNumber) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        writerLabel.setText("Queue: " + formatter.format(alreadyFinishedNumber));
    }

    public void showReaderProgress(int alreadyReadNumber) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        readerLabel.setText("Read KBs: " + formatter.format(alreadyReadNumber));
    }

}
