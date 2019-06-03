package kb_creator.Observer;

import kb_creator.gui.statusPanel.MainStatusPanel;
import kb_creator.gui.statusPanel.MemoryPanel;
import kb_creator.model.Conditionals.KBCreator;

public class StatusThread implements Runnable {
    private MainStatusPanel mainStatusPanel;
    private KBCreator creatorThread;
    private long idealSleepTime;
    private int lastKBAmount;
    private long lastTimeStamp;
    private MemoryPanel memoryPanel;
    private boolean isRunning = true;


    public StatusThread(MainStatusPanel mainStatusPanel, MemoryPanel memoryPanel) {
        this.mainStatusPanel = mainStatusPanel;
        idealSleepTime = 200;
        lastTimeStamp = System.currentTimeMillis();
        this.memoryPanel = memoryPanel;
    }

    @Override
    public void run() {
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            if (creatorThread != null) {
                mainStatusPanel.showStatus(creatorThread.getStatus());
                mainStatusPanel.showIterationKBs(creatorThread.getIterationNumberOfKBs());
                mainStatusPanel.showKBAmount(creatorThread.getTotalKbAmount());
                mainStatusPanel.showProgress(creatorThread.getCurrentK());
                mainStatusPanel.showSpeed(calcSpeed(creatorThread.getTotalKbAmount()));
                mainStatusPanel.showCurrentCandidatePairs(creatorThread.getCurrentCandidatepairAmount());
                mainStatusPanel.showNextCandidatePairs(creatorThread.getNextCandidatePairAmount());

            } else {
                mainStatusPanel.showStatus(Status.NOT_STARTED);
                mainStatusPanel.showIterationKBs(0);
                mainStatusPanel.showKBAmount(0);
                mainStatusPanel.showProgress(-1);
                mainStatusPanel.showSpeed(0);
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
