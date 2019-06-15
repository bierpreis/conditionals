package kb_creator.gui.writerPanel.candidatesPanel;

import com.intellij.ui.components.JBPanel;

import javax.swing.*;

public class MainCandidatesPanel extends JBPanel {
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
        setStatus(CandidateStatus.NOT_STARTED);
        setProgress(0);
    }

    public void setStatus(CandidateStatus status) {
        statusLabel.setText("Status: " + status.toString());
    }

    public void setProgress(int alreadyFinishedNumber) {
        progressLabel.setText("Already finished: " + alreadyFinishedNumber);
    }
}
