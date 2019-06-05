package kb_creator.Observer;

import kb_creator.gui.KBMainWindow;
import kb_creator.model.Conditionals.KBCreator;
import kb_creator.model.Writers.KBWriter.AbstractKbWriter;
import kb_creator.model.Writers.KBWriter.KbFileWriter;

public class StatusThread implements Runnable {
    private KBCreator creatorThread;
    private long idealSleepTime;
    private int lastKBAmount;
    private long lastTimeStamp;
    private boolean isRunning = true;
    private AbstractKbWriter kbWriter;
    private KBMainWindow mainWindow;


    public StatusThread(KBMainWindow mainWindow) {
        this.mainWindow = mainWindow;
        idealSleepTime = 200;
        lastTimeStamp = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            if (creatorThread != null) {
                mainWindow.getCreatorPanel().showStatus(creatorThread.getStatus());
                mainWindow.getCreatorPanel().showIterationKBs(creatorThread.getIterationNumberOfKBs());
                mainWindow.getCreatorPanel().showKBAmount(creatorThread.getTotalKbAmount());
                mainWindow.getCreatorPanel().showFinishedKbs(creatorThread.getCurrentK());
                mainWindow.getCreatorPanel().showSpeed(calcSpeed(creatorThread.getTotalKbAmount()));
                mainWindow.getCreatorPanel().showCurrentCandidatePairs(creatorThread.getCurrentCandidatepairAmount());
                mainWindow.getCreatorPanel().showNextCandidatePairs(creatorThread.getNextCandidatePairAmount());
                mainWindow.getMainWriterPanel().getQueuePanel().showConsistentQueue(kbWriter.getConsistentQueue());
                mainWindow.getMainWriterPanel().getQueuePanel().showInconsistentQueue(kbWriter.getInconsistentQueue());
                mainWindow.getMainWriterPanel().getWriterPanel().showSpeed(kbWriter.getConsistentCounter() + kbWriter.getInconsistentCounter());
                mainWindow.getMainWriterPanel().getWriterPanel().showConsistentConter(kbWriter.getConsistentCounter());
                mainWindow.getMainWriterPanel().getWriterPanel().showIncosnsistentCounter(kbWriter.getInconsistentCounter());

            }

            mainWindow.getCreatorPanel().getMemoryPanel().showFreeMemory();
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
