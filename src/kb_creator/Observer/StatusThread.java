package kb_creator.Observer;

import kb_creator.gui.leftpanel.InfoPanel;
import kb_creator.model.KBCreator;

public class StatusThread implements Runnable {
    private InfoPanel infoPanel;
    private KBCreator creatorThread;


    public StatusThread(InfoPanel infoPanel, KBCreator creatorThread) {
        this.infoPanel = infoPanel;
        this.creatorThread = creatorThread;
    }

    @Override
    public void run() {
        while (!creatorThread.getStatus().equals(Status.STOPPED)) {
            infoPanel.showStatus(creatorThread.getStatus());
            infoPanel.showCandidatePairAmount(creatorThread.getCandidatePairAmount());
            infoPanel.showKBAmount(creatorThread.getKBAmount());
            infoPanel.showProgress(creatorThread.getProgressInPercent());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
