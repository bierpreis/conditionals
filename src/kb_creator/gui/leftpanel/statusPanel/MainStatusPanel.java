package kb_creator.gui.leftpanel.statusPanel;

import kb_creator.Observer.Status;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;

public class MainStatusPanel extends JPanel {
    private OverallStatusPanel overallStatusPanel;
    private CurrentIterationPanel currentIterationPanel;
    private NextIterationPanel nextIterationPanel;

    public MainStatusPanel(ActionPanel actionPanel) {
        setBorder(BorderFactory.createTitledBorder("Status"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());

        overallStatusPanel = new OverallStatusPanel(actionPanel);
        add(overallStatusPanel);

        currentIterationPanel = new CurrentIterationPanel();
        add(currentIterationPanel);

        nextIterationPanel = new NextIterationPanel();
        add(nextIterationPanel);

    }

    public void showProgress(int finishedKBs) {
        currentIterationPanel.showOverallProgress(finishedKBs);
    }

    public void showIterationKBs(int candidatePairAmount) {
        currentIterationPanel.showIterationKBs(candidatePairAmount);
    }

    public void showKBAmount(int kbAmount) {
        overallStatusPanel.showKBs(kbAmount);
    }

    public void showStatus(Status status) {
        overallStatusPanel.showStatus(status);
    }

    public void showSpeed(int speed) {
        currentIterationPanel.showSpeed(speed);
    }

    public void showCurrentCandidatePairs(int candidatePairs) {
        currentIterationPanel.showCandidatePairs(candidatePairs);
    }

    public void showNextCandidatePairs(int nextCandidatepairs) {
        nextIterationPanel.showCandidates(nextCandidatepairs);
    }
}
