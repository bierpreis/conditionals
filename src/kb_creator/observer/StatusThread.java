package kb_creator.observer;

import kb_creator.gui.MainWindow;
import kb_creator.model.creator.AbstractCreator;
import kb_creator.model.creator.SimpleCreator;
import kb_creator.model.buffer.BlockingPairBuffer;
import kb_creator.model.buffer.ConcurrentPairBuffer;
import kb_creator.model.writer.AbstractKbWriter;

public class StatusThread implements Runnable {
    private AbstractCreator creatorThread;
    private long sleepTime;
    private int lastKBAmount;
    private long lastTimeStamp;
    private boolean isRunning = true;
    private AbstractKbWriter kbWriter;
    private MainWindow mainWindow;


    public StatusThread(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        sleepTime = 400;
        lastTimeStamp = System.currentTimeMillis();


    }


    @Override
    public void run() {
        while (isRunning) {
            if (creatorThread != null) {

                showCreatorStatus();

                showWriterStatus();

                showBufferStatus();

                checkIfNotifyBuffer();
            }

            mainWindow.getRightPanel().getMemoryPanel().showFreeMemory();

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    private void showCreatorStatus() {

        mainWindow.getMidPanel().getCreatorPanel().showStatus(creatorThread.getCreatorStatus());
        mainWindow.getMidPanel().getCreatorPanel().showIterationKBs(kbWriter.getIterationConsistentKbCounter());
        mainWindow.getMidPanel().getCreatorPanel().showConsistentKBAmount(creatorThread.getTotalKbAmount());
        mainWindow.getMidPanel().getCreatorPanel().showInconsistentKBAmount(creatorThread.getTotalInconsistentAmount());
        mainWindow.getMidPanel().getCreatorPanel().showProgress(creatorThread.getProgress());
        mainWindow.getMidPanel().getCreatorPanel().showCurrentK(creatorThread.getCurrentK());
        mainWindow.getMidPanel().getCreatorPanel().showSpeed(calcSpeed(creatorThread.getTotalKbAmount()));
        mainWindow.getMidPanel().getCreatorPanel().showCurrentCandidatePairs(creatorThread.getLastPairAmount());
        mainWindow.getMidPanel().getCreatorPanel().showNextCandidatePairs(creatorThread.getNextCandidatePairAmount());

        if (creatorThread.getCreatorStatus().equals(SimpleCreator.CreatorStatus.RUNNING))
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

            mainWindow.getRightPanel().getBufferStatusPanel().showReaderBuffer(creatorThread.getPairBuffer().getReaderBufferSize());
            mainWindow.getRightPanel().getBufferStatusPanel().showWriterQueue(creatorThread.getPairBuffer().getQueueToWriteSize());

        }
    }

    //todo: is this rly needed? buffer should have 1 writer and 1 reader thread and blocking queues. then this can be deleted
    private void checkIfNotifyBuffer() {
        if (creatorThread.getPairBuffer() instanceof BlockingPairBuffer || creatorThread.getPairBuffer() instanceof ConcurrentPairBuffer) {
            if (creatorThread.getPairBuffer().checkIfShouldRead() || creatorThread.getPairBuffer().checkIfShouldWrite())
                synchronized (creatorThread.getPairBuffer()) {
                    creatorThread.getPairBuffer().notifyBuffer();
                }

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

    public void setCreatorThread(AbstractCreator kbCreator) {
        this.creatorThread = kbCreator;
        this.kbWriter = creatorThread.getKbWriterThread();
    }
}
