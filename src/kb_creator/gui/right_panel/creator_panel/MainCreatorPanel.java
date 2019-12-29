package kb_creator.gui.right_panel.creator_panel;

import kb_creator.model.creator.CreatorStatus;
import kb_creator.gui.left_panel.ActionPanel;

import javax.swing.*;
import java.awt.*;

public class MainCreatorPanel extends JPanel {

    private OverallStatusPanel overallStatusPanel;
    private CurrentIterationPanel currentIterationPanel;

    public MainCreatorPanel(ActionPanel actionPanel) {
        setPreferredSize(new Dimension(350, 300));
        setBorder(BorderFactory.createTitledBorder("KB Creator Status"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //add(Box.createVerticalGlue());

        overallStatusPanel = new OverallStatusPanel(actionPanel);
        add(overallStatusPanel);

        currentIterationPanel = new CurrentIterationPanel();
        add(currentIterationPanel);


        showStatus(CreatorStatus.NOT_STARTED);

        showTotalConsistentKBAmount(0);

        showIterationInconsistentKBs(0);

        showProgress(0);

        showCurrentCandidatePairs(0);
    }

    public void showCurrentK(int currentK) {
        currentIterationPanel.showCurrentK(currentK);
    }

    public void showIterationConsistentKBs(long iterationConsistentAmount) {
        currentIterationPanel.showIterationConsistentAmount(iterationConsistentAmount);
    }

    public void showIterationInconsistentKBs(long inconsistentIterationAmount){
        currentIterationPanel.showIterationInconsistentAmount(inconsistentIterationAmount);
    }

    public void showTotalConsistentKBAmount(long kbAmount) {
        overallStatusPanel.showConsistentKBAmount(kbAmount);
    }

    public void showTotalInconsistentKBAmount(long kbAmount) {
        overallStatusPanel.showInconsistentKBAmount(kbAmount);
    }

    public void showStatus(CreatorStatus creatorStatus) {
        overallStatusPanel.showStatus(creatorStatus);
    }

    public void showCurrentCandidatePairs(long candidatePairs) {
        currentIterationPanel.showCandidatePairs(candidatePairs);
    }

    public void showProgress(float progress) {
        currentIterationPanel.showProgress(progress);
    }

    public void showTime(long startTime) {
        overallStatusPanel.showRunningTime(startTime);
    }

    public void showAverageSpeed(long kbAmount, long startTime){overallStatusPanel.showAverageSpeed(kbAmount, startTime);}

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }


}
