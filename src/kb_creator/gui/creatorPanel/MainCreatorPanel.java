package kb_creator.gui.creatorPanel;

import kb_creator.Observer.Status;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;
import kb_creator.gui.writerPanel.MainWriterPanel;
import kb_creator.gui.writerPanel.WriterPanel;

import javax.swing.*;
import java.awt.*;

public class MainCreatorPanel extends JPanel {

    private OverallStatusPanel overallStatusPanel;
    private CurrentIterationPanel currentIterationPanel;
    private NextIterationPanel nextIterationPanel;
    private MemoryPanel memoryPanel;
    private MainWriterPanel mainWriterPanel;

    public MainCreatorPanel(ActionPanel actionPanel) {
        setBorder(BorderFactory.createTitledBorder("Creator"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());

        overallStatusPanel = new OverallStatusPanel(actionPanel);
        add(overallStatusPanel);

        currentIterationPanel = new CurrentIterationPanel();
        add(currentIterationPanel);

        nextIterationPanel = new NextIterationPanel();
        add(nextIterationPanel);

        add(memoryPanel = new MemoryPanel());

        mainWriterPanel = new MainWriterPanel();
        add(mainWriterPanel);

        //setPreferredSize(new Dimension(400, 500));


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

    public MemoryPanel getMemoryPanel() {
        return memoryPanel;
    }

    public void showConsistentQueue(int consistentLength) {
        mainWriterPanel.getQueuePanel().showConsistentQueue((consistentLength));
    }

    public void showInconsistentQueue(int inconsistentleghth) {
        mainWriterPanel.getQueuePanel().showInconsistentQueue(inconsistentleghth);
    }

    public void showConsistentCounter(int consistentCounter){
        mainWriterPanel.getMainWriterPanel().showConsistentConter(consistentCounter);
    }

    public void showInconsistentCounter(int inconsistentCounter){
        mainWriterPanel.getMainWriterPanel().showIncosnsistentCounter(inconsistentCounter);
    }
}
