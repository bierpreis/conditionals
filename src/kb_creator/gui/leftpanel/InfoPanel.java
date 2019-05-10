package kb_creator.gui.leftpanel;

import kb_creator.Observer.Status;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;

public class InfoPanel extends JPanel {
    private SizePanel sizePanel;
    private StatusPanel statusPanel;

    public InfoPanel(ActionPanel actionPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());

        sizePanel = new SizePanel();
        add(sizePanel);

        statusPanel = new StatusPanel(actionPanel);
        add(statusPanel);

    }

    public void showProgress(int finishedKBs) {
        statusPanel.showProgress(finishedKBs);
    }

    public void showCandidatePairAmount(int candidatePairAmount) {
        sizePanel.showCandidatePairs(candidatePairAmount);
    }

    public void showKBAmount(int kbAmount) {
        sizePanel.showKBs(kbAmount);
    }

    public void showStatus(Status status){
        statusPanel.showStatus(status);
    }

    public void showSpeed(int speed){statusPanel.showSpeed(speed);}
}
