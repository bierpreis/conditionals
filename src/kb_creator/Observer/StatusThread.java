package kb_creator.Observer;

import kb_creator.gui.leftpanel.InfoPanel;
import kb_creator.model.KBCreator;

public class StatusThread implements Runnable {
    private InfoPanel infoPanel;
    private KBCreator creatorThread;
    private volatile Status status;

    public StatusThread(InfoPanel infoPanel, KBCreator creatorThread) {
        System.out.println("status thread created");
        this.infoPanel = infoPanel;
        this.creatorThread = creatorThread;
        status = Status.NOT_STARTED;
    }

    //todo: show status when creating nfc too? problem: this thread is started after nfc gets created. fix this?
    @Override
    public void run() {
        while (!creatorThread.isStopped()) {
            infoPanel.showStatus(creatorThread.getStatus());
            infoPanel.showCandidatePairAmount(creatorThread.getCandidatePairAmount());
            infoPanel.showKBAmount(creatorThread.getKBAmount());
            infoPanel.showProgress(creatorThread.getProgressInPercent());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
