package kb_creator.observer;

import kb_creator.gui.MainWindow;
import kb_creator.model.conditionals.KBCreator;
import kb_creator.model.kb_writer.AbstractKbWriter;

public class StatusThread implements Runnable {
    private KBCreator creatorThread;
    private long idealSleepTime;
    private int lastKBAmount;
    private long lastTimeStamp;
    private boolean isRunning = true;
    private AbstractKbWriter kbWriter;
    private MainWindow mainWindow;


    public StatusThread(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        idealSleepTime = 200;
        lastTimeStamp = System.currentTimeMillis();
    }


    @Override
    public void run() {
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            if (creatorThread != null) {
                mainWindow.getLeftPanel().getMainOptionsPanel().setActive(false);
                mainWindow.getMidPanel().getCreatorPanel().showStatus(creatorThread.getStatus());
                mainWindow.getMidPanel().getCreatorPanel().showIterationKBs(creatorThread.getIterationNumberOfKBs());
                mainWindow.getMidPanel().getCreatorPanel().showConsistentKBAmount(creatorThread.getTotalKbAmount());
                mainWindow.getMidPanel().getCreatorPanel().showInconsistentKBAmount(creatorThread.getTotalInconsistentAmount());
                mainWindow.getMidPanel().getCreatorPanel().showProgress(creatorThread.getProgress());
                mainWindow.getMidPanel().getCreatorPanel().showCurrentK(creatorThread.getCurrentK());
                mainWindow.getMidPanel().getCreatorPanel().showSpeed(calcSpeed(creatorThread.getTotalKbAmount()));


                mainWindow.getMidPanel().getCreatorPanel().showCurrentCandidatePairs(creatorThread.getLastPairAmount());

                mainWindow.getMidPanel().getCreatorPanel().showNextCandidatePairs(creatorThread.getNextCandidatePairAmount());

                if (creatorThread.getStatus().equals(Status.RUNNING))
                    mainWindow.getMidPanel().getCreatorPanel().showTime(creatorThread.getStartTime());


                mainWindow.getRightPanel().getWriterStatusPanel().showConsistentQueue(kbWriter.getConsistentQueue());
                mainWindow.getRightPanel().getWriterStatusPanel().showInconsistentQueue(kbWriter.getInconsistentQueue());


                mainWindow.getRightPanel().getWriterStatusPanel().showSpeed(kbWriter.getConsistentCounter() + kbWriter.getInconsistentCounter());
                mainWindow.getRightPanel().getWriterStatusPanel().showConsistentConter(kbWriter.getConsistentCounter());
                mainWindow.getRightPanel().getWriterStatusPanel().showIncosnsistentCounter(kbWriter.getInconsistentCounter());


                //cp writer thread is started after this thread, so this will avoid the null pointer exception
                if (creatorThread.getPairBuffer() != null) {
                    mainWindow.getRightPanel().getCandidatesPanel().showWriterQueue(creatorThread.getPairBuffer().getQueueToWriteSize());
                    mainWindow.getRightPanel().getCandidatesPanel().showStatus(creatorThread.getPairBuffer().getStatus());
                }


                if ((kbWriter.getConsistentQueue() + kbWriter.getInconsistentQueue()) > 100_000)

                    creatorThread.waitForKbWriter();

                if ((kbWriter.getConsistentQueue() + kbWriter.getInconsistentQueue()) < 5000)
                    synchronized (creatorThread) {
                        creatorThread.notify();
                    }

            }

            mainWindow.getMidPanel().getMemoryPanel().showFreeMemory();
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
        this.kbWriter = creatorThread.getKbWriterThread();
    }


}
