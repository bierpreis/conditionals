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

    //todo: show status when creating nfc too? problem: this thread is started after nfc gets created. fix this?
    @Override
    public void run() {
        while (creatorThread.isRunning()) {
            infoPanel.showCandidatePairAmount(creatorThread.getCandidatePairAmount());
            infoPanel.showKBAmount(creatorThread.getKBAmount());
            infoPanel.showIfStillRunning(creatorThread.isRunning());
            infoPanel.showProgress(creatorThread.getProgressInPercent());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
