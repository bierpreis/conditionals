package kb_creator.Observer;

import kb_creator.gui.leftpanel.InfoPanel;
import kb_creator.model.KBCreator;

public class StatusThread implements Runnable {
    private InfoPanel infoPanel;
    private KBCreator creatorThread;
    private long idealSleepTime;
    private int lastKBAmount;
    private long lastTimeStamp;
    private boolean isRunning;


    public StatusThread(InfoPanel infoPanel, KBCreator creatorThread) {
        this.infoPanel = infoPanel;
        this.creatorThread = creatorThread;
        idealSleepTime = 200;
        lastTimeStamp = System.currentTimeMillis();
    }

    @Override
    public void run() {
        //todo: maybe dont end thread when creator is ended?
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            infoPanel.showStatus(creatorThread.getStatus());
            infoPanel.showCandidatePairAmount(creatorThread.getCandidatePairAmount());
            infoPanel.showKBAmount(creatorThread.getKBAmount());
            infoPanel.showProgress(creatorThread.getProgressInPercent());
            infoPanel.showSpeed(calcSpeed(creatorThread.getKBAmount()));

            long iterationTime = System.currentTimeMillis() - startTime;
            long sleepTime = idealSleepTime - iterationTime;
            if (sleepTime > 0)
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


        }
        //todo: not sure if this is still needed. maybe delelte.
        //this is to display status correctly when thread finishes
        infoPanel.showStatus(creatorThread.getStatus());
        infoPanel.showCandidatePairAmount(creatorThread.getCandidatePairAmount());
        infoPanel.showKBAmount(creatorThread.getKBAmount());
        infoPanel.showProgress(creatorThread.getProgressInPercent());
        infoPanel.showSpeed(calcSpeed(creatorThread.getKBAmount()));


    }

    private int calcSpeed(int kbAmount) {
        int kbIncrease = kbAmount - lastKBAmount;
        lastKBAmount = kbAmount;

        float timeInSeconds = (System.currentTimeMillis() - lastTimeStamp) / 1000f;
        int kbPerSecond = (int) (kbIncrease / timeInSeconds);

        lastTimeStamp = System.currentTimeMillis();

        return kbPerSecond;
    }

    public void stopThread() {
        isRunning = false;
    }
}
