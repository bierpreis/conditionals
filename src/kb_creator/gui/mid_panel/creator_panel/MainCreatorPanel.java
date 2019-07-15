package kb_creator.gui.mid_panel.creator_panel;

import kb_creator.observer.Status;
import kb_creator.gui.left_panel.actionpanel.ActionPanel;

import javax.swing.*;
import java.awt.*;

public class MainCreatorPanel extends JPanel {

    private OverallStatusPanel overallStatusPanel;
    private CurrentIterationPanel currentIterationPanel;
    private NextIterationPanel nextIterationPanel;

    public MainCreatorPanel(ActionPanel actionPanel) {
        setPreferredSize(new Dimension(350, 300));
        setBorder(BorderFactory.createTitledBorder("KB Creator"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //add(Box.createVerticalGlue());

        overallStatusPanel = new OverallStatusPanel(actionPanel);
        add(overallStatusPanel);

        currentIterationPanel = new CurrentIterationPanel();
        add(currentIterationPanel);

        nextIterationPanel = new NextIterationPanel();
        add(nextIterationPanel);

        showStatus(Status.NOT_STARTED);

        showConsistentKBAmount(0);

        showProgress(0);

        showCurrentCandidatePairs(0);
    }

    public void showCurrentK(int finishedKBs) {
        currentIterationPanel.showCurrentK(finishedKBs);
    }

    public void showIterationKBs(int candidatePairAmount) {
        currentIterationPanel.showIterationKBs(candidatePairAmount);
    }

    public void showConsistentKBAmount(int kbAmount) {
        overallStatusPanel.showConsistentKBAmount(kbAmount);
    }

    public void showInconsistentKBAmount(int kbAmount) {
        overallStatusPanel.showInconsistentKBAmount(kbAmount);
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

    public void showProgress(float progress) {
        currentIterationPanel.showProgress(progress);
    }

    public void showTime(long startTime) {
        overallStatusPanel.showTime(startTime);
    }


}
