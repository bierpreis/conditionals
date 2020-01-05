package kb_creator.observer;

import kb_creator.gui.MainWindow;
import kb_creator.model.creator.CreatorStatus;
import kb_creator.model.creator.GenKB;
import kb_creator.model.writer.AbstractKbWriter;

public class GuiStatusThread implements Runnable {
    private GenKB creatorThread;
    private long sleepTime;
    private int lastKBAmount;
    private long lastTimeStamp;
    private AbstractKbWriter kbWriter;
    private MainWindow mainWindow;


    public GuiStatusThread(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        sleepTime = 400;
        lastTimeStamp = System.currentTimeMillis();


    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (creatorThread != null) {

                showCreatorStatus();

                showWriterStatus();

                showBufferStatus();

                checkIfFinished();
            }

            mainWindow.getRightPanel().getMemoryPanel().showFreeMemory();

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    private void checkIfFinished() {
        if (creatorThread.getCreatorStatus().equals(CreatorStatus.FINISHED)) {
            mainWindow.getRightPanel().setActive(false);
            mainWindow.getLeftPanel().getMainOptionsPanel().setActive(true);
        }
    }

    private void showCreatorStatus() {

        mainWindow.getRightPanel().getCreatorPanel().showStatus(creatorThread.getCreatorStatus());


        mainWindow.getRightPanel().getCreatorPanel().showTotalConsistentKBAmount(creatorThread.getTotalKbAmount());
        mainWindow.getRightPanel().getCreatorPanel().showTotalInconsistentKBAmount(creatorThread.getTotalInconsistentAmount());
        mainWindow.getRightPanel().getCreatorPanel().showProgress(creatorThread.calculateProgress());
        mainWindow.getRightPanel().getCreatorPanel().showCurrentK(creatorThread.getCurrentK());
        mainWindow.getRightPanel().getCreatorPanel().showCurrentCandidatePairs(creatorThread.getCurrentPairAmount());


        if (creatorThread.getCreatorStatus().equals(CreatorStatus.RUNNING)) {
            mainWindow.getRightPanel().getCreatorPanel().showTime(creatorThread.getStartTime());
            mainWindow.getRightPanel().getCreatorPanel().showAverageSpeed(creatorThread.getTotalKbAmount(), creatorThread.getStartTime());
        }
    }

    private void showWriterStatus() {
        mainWindow.getRightPanel().getCreatorPanel().showIterationConsistentKBs(kbWriter.getIterationConsistentCounter());
        mainWindow.getRightPanel().getCreatorPanel().showIterationInconsistentKBs(kbWriter.getIterationInconsistentCounter());

        mainWindow.getRightPanel().getWriterStatusPanel().showConsistentQueue(kbWriter.getConsistentQueue().size());
        mainWindow.getRightPanel().getWriterStatusPanel().showInconsistentQueue(kbWriter.getInconsistentQueue().size());

        mainWindow.getRightPanel().getWriterStatusPanel().showStatus(kbWriter.getStatus());
    }

    private void showBufferStatus() {
        //cp writer thread is started after this thread, so this will avoid the null pointer exception
        if (creatorThread.getPairBuffer() != null) {

            mainWindow.getRightPanel().getBufferStatusPanel().showReaderBuffer(creatorThread.getPairBuffer().getReaderBufferSize());
            mainWindow.getRightPanel().getBufferStatusPanel().showWriterQueue(creatorThread.getPairBuffer().getQueueToWriteSize());
            mainWindow.getRightPanel().getBufferStatusPanel().showType(mainWindow.getBufferingType());

        }
    }


    private int calcSpeed(int kbAmount) {
        int kbGrowthNumber = kbAmount - lastKBAmount;
        lastKBAmount = kbAmount;

        float timeInSeconds = (System.currentTimeMillis() - lastTimeStamp) / 1000f;
        int kbPerSecond = (int) (kbGrowthNumber / timeInSeconds);
        lastTimeStamp = System.currentTimeMillis();

        return kbPerSecond;
    }

    public void setCreatorThread(GenKB genKB) {
        this.creatorThread = genKB;
        this.kbWriter = creatorThread.getKbWriterThread();
    }
}
