package kb_creator.Observer;

import kb_creator.gui.leftpanel.InfoPanel;
import kb_creator.gui.leftpanel.MemoryPanel;
import kb_creator.model.Conditionals.KBCreator;

public class StatusThread implements Runnable {
    private InfoPanel infoPanel;
    private KBCreator creatorThread;
    private long idealSleepTime;
    private int lastKBAmount;
    private long lastTimeStamp;
    private MemoryPanel memoryPanel;
    private boolean isRunning = true;


    public StatusThread(InfoPanel infoPanel, MemoryPanel memoryPanel) {
        this.infoPanel = infoPanel;
        idealSleepTime = 200;
        lastTimeStamp = System.currentTimeMillis();
        this.memoryPanel = memoryPanel;
    }

    @Override
    public void run() {
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            if (creatorThread != null) {
                infoPanel.showStatus(creatorThread.getStatus());
                infoPanel.showIterationKBs(creatorThread.getIterationNumberOfKBs());
                infoPanel.showKBAmount(creatorThread.getTotalKbAmount());
                infoPanel.showProgress(creatorThread.getCurrentK());
                infoPanel.showSpeed(calcSpeed(creatorThread.getTotalKbAmount()));
                infoPanel.showCandidatePairs(creatorThread.getCurrentCandidatepairAmount());

            } else {
                infoPanel.showStatus(Status.NOT_STARTED);
                infoPanel.showIterationKBs(0);
                infoPanel.showKBAmount(0);
                infoPanel.showProgress(-1);
                infoPanel.showSpeed(0);
            }

            memoryPanel.showFreeMemory();
            long iterationTime = System.currentTimeMillis() - startTime;
            long sleepTime = idealSleepTime - iterationTime;
            if (sleepTime > 0)
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


        }


    }

    private int calcSpeed(int kbAmount) {
        int kbIncrease = kbAmount - lastKBAmount;
        lastKBAmount = kbAmount;

        float timeInSeconds = (System.currentTimeMillis() - lastTimeStamp) / 1000f;
        int kbPerSecond = (int) (kbIncrease / timeInSeconds);

        lastTimeStamp = System.currentTimeMillis();

        return kbPerSecond;
    }

    public void setCreatorThread(KBCreator kbCreator) {
        this.creatorThread = kbCreator;
    }

}
