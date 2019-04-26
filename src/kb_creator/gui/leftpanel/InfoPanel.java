package kb_creator.gui.leftpanel;

import javax.swing.*;

public class InfoPanel extends JPanel {
    private SizePanel sizePanel;
    private StatusPanel statusPanel;

    public InfoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());

        sizePanel = new SizePanel();
        add(sizePanel);

        statusPanel = new StatusPanel();
        add(statusPanel);

    }

    public void showIfStillRunning(boolean isRuning) {
        statusPanel.showIfStillRunning(isRuning);
    }

    public void showProgress(float progressInPercent) {
        statusPanel.showProgress(progressInPercent);
    }

    public void showCandidatePairAmount(int candidatePairAmount) {
        sizePanel.showCandidatePairs(candidatePairAmount);
    }

    public void showKBAmount(int kbAmount) {
        sizePanel.showKBs(kbAmount);
    }
}
