package kb_creator.Observer;

import kb_creator.gui.leftpanel.InfoPanel;
import kb_creator.model.KBCreator;

public class StatusThread implements Runnable {
    private InfoPanel infoPanel;
    private KBCreator creatorThread;
    private long sleepTime;
    private int lastKBAmount;


    public StatusThread(InfoPanel infoPanel, KBCreator creatorThread) {
        this.infoPanel = infoPanel;
        this.creatorThread = creatorThread;
        sleepTime = 100;
    }

    @Override
    public void run() {
        while (!creatorThread.getStatus().equals(Status.STOPPED)) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            infoPanel.showStatus(creatorThread.getStatus());
            infoPanel.showCandidatePairAmount(creatorThread.getCandidatePairAmount());
            infoPanel.showKBAmount(creatorThread.getKBAmount());
            infoPanel.showProgress(creatorThread.getProgressInPercent());
            infoPanel.showSpeed(calcSpeed(creatorThread.getKBAmount()));


            if (creatorThread.getStatus().equals(Status.FINISHED))
                break;
        }


    }

    private int calcSpeed(int kbAmount) {
        int speed = (int) (((kbAmount - lastKBAmount) / sleepTime) * (1000 / sleepTime));
        lastKBAmount = kbAmount;
        return speed;
    }
}
