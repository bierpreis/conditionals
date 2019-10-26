package kb_creator.observer;

import kb_creator.gui.MainWindow;
import kb_creator.model.creator.AbstractCreator;
import kb_creator.model.creator.SimpleCreator;
import kb_creator.model.writer.AbstractKbWriter;

//todo: rename sth with gui?
public class StatusThread implements Runnable {
    private AbstractCreator creatorThread;
    private long sleepTime;
    private int lastKBAmount;
    private long lastTimeStamp;
    private boolean isRunning = true; //todo: delete?
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
            }

            mainWindow.getMidPanel().getMemoryPanel().showFreeMemory();

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    private void showCreatorStatus() {

        mainWindow.getMidPanel().getCreatorPanel().showStatus(creatorThread.getCreatorStatus());


        mainWindow.getMidPanel().getCreatorPanel().showTotalConsistentKBAmount(creatorThread.getTotalKbAmount());
        mainWindow.getMidPanel().getCreatorPanel().showTotalInconsistentKBAmount(creatorThread.getTotalInconsistentAmount());
        mainWindow.getMidPanel().getCreatorPanel().showProgress(creatorThread.getProgress());
        mainWindow.getMidPanel().getCreatorPanel().showCurrentK(creatorThread.getCurrentK());
        mainWindow.getMidPanel().getCreatorPanel().showSpeed(calcSpeed(creatorThread.getTotalKbAmount()));
        mainWindow.getMidPanel().getCreatorPanel().showCurrentCandidatePairs(creatorThread.getCurrentPairAmount());



        if (creatorThread.getCreatorStatus().equals(SimpleCreator.CreatorStatus.RUNNING)) {
            mainWindow.getMidPanel().getCreatorPanel().showTime(creatorThread.getStartTime());
            mainWindow.getMidPanel().getCreatorPanel().showAverageSpeed(creatorThread.getTotalKbAmount(), creatorThread.getStartTime());
        }
    }

    private void showWriterStatus() {
        mainWindow.getMidPanel().getCreatorPanel().showIterationConsistentKBs(kbWriter.getIterationConsistentCounter());
        mainWindow.getMidPanel().getCreatorPanel().showIterationInconsistentKBs(kbWriter.getIterationInconsistentCounter());

        mainWindow.getMidPanel().getWriterStatusPanel().showConsistentQueue(kbWriter.getConsistentQueue());
        mainWindow.getMidPanel().getWriterStatusPanel().showInconsistentQueue(kbWriter.getInconsistentQueue());

        mainWindow.getMidPanel().getWriterStatusPanel().showStatus(kbWriter.getStatus());
    }

    private void showBufferStatus() {
        //cp writer thread is started after this thread, so this will avoid the null pointer exception
        if (creatorThread.getPairBuffer() != null) {

            mainWindow.getMidPanel().getBufferStatusPanel().showReaderBuffer(creatorThread.getPairBuffer().getReaderBufferSize());
            mainWindow.getMidPanel().getBufferStatusPanel().showWriterQueue(creatorThread.getPairBuffer().getQueueToWriteSize());

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
