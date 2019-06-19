package kb_creator.gui.writerpanel;

import kb_creator.model.cp_buffer.CandidateStatus;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class MainCandidatesPanel extends JPanel {
    private JLabel statusLabel;
    private JLabel progressLabel;

    public MainCandidatesPanel() {
        setBorder(BorderFactory.createTitledBorder("Candidates Buffer"));


        Box vBox = Box.createVerticalBox();
        add(vBox);

        statusLabel = new JLabel();
        progressLabel = new JLabel();
        vBox.add(statusLabel);
        vBox.add(progressLabel);
        showStatus(CandidateStatus.NOT_STARTED);
        showProgress(0);

    }

    public void showStatus(CandidateStatus status) {
        statusLabel.setText("Status: " + status.toString());
    }

    public void showProgress(int alreadyFinishedNumber) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        progressLabel.setText("Queue: " + formatter.format(alreadyFinishedNumber));
    }

}
