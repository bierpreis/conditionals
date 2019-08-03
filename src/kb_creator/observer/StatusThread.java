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
        idealSleepTime = 400;
        lastTimeStamp = System.currentTimeMillis();
    }


    @Override
    public void run() {
        while (isRunning) {
            long startTime = System.currentTimeMillis();

            if (creatorThread != null) {

                showCreatorStatus();

                showWriterStatus();

                showBufferStatus();

                checkIfWaitForWriter();
            }

            mainWindow.getRightPanel().getMemoryPanel().showFreeMemory();


            sleep(startTime);

        }


    }

    private void showCreatorStatus() {
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
    }

    private void showWriterStatus() {
        mainWindow.getRightPanel().getWriterStatusPanel().showConsistentQueue(kbWriter.getConsistentQueue());
        mainWindow.getRightPanel().getWriterStatusPanel().showInconsistentQueue(kbWriter.getInconsistentQueue());


        mainWindow.getRightPanel().getWriterStatusPanel().showSpeed(kbWriter.getConsistentCounter() + kbWriter.getInconsistentCounter());
        mainWindow.getRightPanel().getWriterStatusPanel().showConsistentConter(kbWriter.getConsistentCounter());
        mainWindow.getRightPanel().getWriterStatusPanel().showIncosnsistentCounter(kbWriter.getInconsistentCounter());
        mainWindow.getRightPanel().getWriterStatusPanel().showStatus(kbWriter.getStatus());
    }

    private void showBufferStatus() {
        //cp writer thread is started after this thread, so this will avoid the null pointer exception
        if (creatorThread.getPairBuffer() != null) {
            mainWindow.getRightPanel().getBufferStatusPanel().showStatus(creatorThread.getPairBuffer().getStatus());

            //todo: show reader queue
            mainWindow.getRightPanel().getBufferStatusPanel().showWriterQueue(creatorThread.getPairBuffer().getQueueToWriteSize());

        }
    }

    private void checkIfWaitForWriter() {
        if ((kbWriter.getConsistentQueue() + kbWriter.getInconsistentQueue()) > 100_000)
            creatorThread.waitForKbWriter();

        if ((kbWriter.getConsistentQueue() + kbWriter.getInconsistentQueue()) < 5000)
            synchronized (creatorThread) {
                creatorThread.notify();
            }
    }

    private void sleep(long startTime) {
        long iterationTime = System.currentTimeMillis() - startTime;
        long sleepTime = idealSleepTime - iterationTime;
        if (sleepTime > 0)
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


    }

    //todo: make this more smooth?!
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
