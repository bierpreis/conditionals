package kb_creator.Observer;

import kb_creator.gui.leftpanel.InfoPanel;
import kb_creator.model.KBCreator;

public class StatusThread implements Runnable {
    private InfoPanel infoPanel;
    private KBCreator creatorThread;
    private long sleepTime;
    private int lastKBAmount;
    private long lastTimeStamp;


    public StatusThread(InfoPanel infoPanel, KBCreator creatorThread) {
        this.infoPanel = infoPanel;
        this.creatorThread = creatorThread;
        sleepTime = 200;
        lastTimeStamp = System.currentTimeMillis();
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

    //todo: check if this is correct
    private int calcSpeed(int kbAmount) {
        int kbIncrease = kbAmount - lastKBAmount;
        long now = System.currentTimeMillis();
        float timeInMs = (now - lastTimeStamp);
        float timeInSeconds = timeInMs / 1000;
        int speed = (int) (kbIncrease / timeInSeconds);
        //int speed = (int) (((kbAmount - lastKBAmount) / ((System.currentTimeMillis() - lastTimeStamp))));
        lastKBAmount = kbAmount;
        lastTimeStamp = System.currentTimeMillis();
        return speed;
    }
}
