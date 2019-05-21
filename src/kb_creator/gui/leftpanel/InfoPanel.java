package kb_creator.gui.leftpanel;

import kb_creator.Observer.Status;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;

public class InfoPanel extends JPanel {
    private SizePanel sizePanel;
    private OverallStatusPanel overallStatusPanel;
    private IterationStatusPanel iterationStatusPanel;

    public InfoPanel(ActionPanel actionPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());

        sizePanel = new SizePanel();
        add(sizePanel);

        overallStatusPanel = new OverallStatusPanel(actionPanel);
        add(overallStatusPanel);

        iterationStatusPanel = new IterationStatusPanel();
        add(iterationStatusPanel);

    }

    public void showProgress(int finishedKBs) {
        iterationStatusPanel.showOverallProgress(finishedKBs);
    }

    public void showCandidatePairAmount(int candidatePairAmount) {
        sizePanel.showCandidatePairs(candidatePairAmount);
    }

    public void showKBAmount(int kbAmount) {
        sizePanel.showKBs(kbAmount);
    }

    public void showStatus(Status status) {
        overallStatusPanel.showStatus(status);
    }

    public void showSpeed(int speed) {
        iterationStatusPanel.showSpeed(speed);
    }
}
