package kb_creator.Observer;

import kb_creator.gui.creatorPanel.MainCreatorPanel;
import kb_creator.gui.creatorPanel.MemoryPanel;
import kb_creator.model.Conditionals.KBCreator;
import kb_creator.model.Writers.KBWriter;

public class StatusThread implements Runnable {
    private MainCreatorPanel mainCreatorPanel;
    private KBCreator creatorThread;
    private long idealSleepTime;
    private int lastKBAmount;
    private long lastTimeStamp;
    private MemoryPanel memoryPanel;
    private boolean isRunning = true;
    private KBWriter kbWriter;


    public StatusThread(MainCreatorPanel mainCreatorPanel, MemoryPanel memoryPanel) {
        this.mainCreatorPanel = mainCreatorPanel;
        idealSleepTime = 200;
        lastTimeStamp = System.currentTimeMillis();
        this.memoryPanel = memoryPanel;
    }

    @Override
    public void run() {
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            if (creatorThread != null) {
                mainCreatorPanel.showStatus(creatorThread.getStatus());
                mainCreatorPanel.showIterationKBs(creatorThread.getIterationNumberOfKBs());
                mainCreatorPanel.showKBAmount(creatorThread.getTotalKbAmount());
                mainCreatorPanel.showProgress(creatorThread.getCurrentK());
                mainCreatorPanel.showSpeed(calcSpeed(creatorThread.getTotalKbAmount()));
                mainCreatorPanel.showCurrentCandidatePairs(creatorThread.getCurrentCandidatepairAmount());
                mainCreatorPanel.showNextCandidatePairs(creatorThread.getNextCandidatePairAmount());
                mainCreatorPanel.showConsistentQueue(kbWriter.getConsistentQueue());
                mainCreatorPanel.showInconsistentQueue(kbWriter.getInconsistetnQueue());

                mainCreatorPanel.showConsistentCounter(kbWriter.getConsitentCounter());
                mainCreatorPanel.showInconsistentCounter(kbWriter.getInconsistentCounter());

            } else {
                mainCreatorPanel.showStatus(Status.NOT_STARTED);
                mainCreatorPanel.showIterationKBs(0);
                mainCreatorPanel.showKBAmount(0);
                mainCreatorPanel.showProgress(-1);
                mainCreatorPanel.showSpeed(0);
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
        System.out.println("setting writer");
        this.kbWriter = creatorThread.getWriterThread();
    }


}
