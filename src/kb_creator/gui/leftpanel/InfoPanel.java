package kb_creator.gui.leftpanel;

import kb_creator.Observer.Status;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;

public class InfoPanel extends JPanel {
    private OverallStatusPanel overallStatusPanel;
    private IterationStatusPanel iterationStatusPanel;

    public InfoPanel(ActionPanel actionPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());

        overallStatusPanel = new OverallStatusPanel(actionPanel);
        add(overallStatusPanel);

        iterationStatusPanel = new IterationStatusPanel();
        add(iterationStatusPanel);

    }

    public void showProgress(int finishedKBs) {
        iterationStatusPanel.showOverallProgress(finishedKBs);
    }

    public void showIterationKBs(int candidatePairAmount) {
        iterationStatusPanel.showIterationKBs(candidatePairAmount);
    }

    public void showKBAmount(int kbAmount) {
        overallStatusPanel.showKBs(kbAmount);
    }

    public void showStatus(Status status) {
        overallStatusPanel.showStatus(status);
    }

    public void showSpeed(int speed) {
        iterationStatusPanel.showSpeed(speed);
    }

    public void showCandidatePairs(int candidatePairs) {
        iterationStatusPanel.showCandidatePairs(candidatePairs);
    }
}
