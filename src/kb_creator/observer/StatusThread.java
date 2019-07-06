package kb_creator.observer;

import kb_creator.gui.KBMainWindow;
import kb_creator.model.conditionals.KBCreator;
import kb_creator.model.kb_writer.AbstractKbWriter;

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


    //todo: order stuff
    @Override
    public void run() {
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            if (creatorThread != null) {
                mainWindow.getCreatorPanel().showStatus(creatorThread.getStatus());
                mainWindow.getCreatorPanel().showIterationKBs(creatorThread.getIterationNumberOfKBs());
                mainWindow.getCreatorPanel().showConsistentKBAmount(creatorThread.getTotalKbAmount());
                mainWindow.getCreatorPanel().showInconsistentKBAmount(creatorThread.getTotalInconsistentAmount());
                mainWindow.getCreatorPanel().showProgress(creatorThread.getProgress());

                mainWindow.getCreatorPanel().showCurrentK(creatorThread.getCurrentK());

                mainWindow.getCreatorPanel().showSpeed(calcSpeed(creatorThread.getTotalKbAmount()));

                mainWindow.getCreatorPanel().showCurrentCandidatePairs(creatorThread.getLastPairAmount());

                mainWindow.getCreatorPanel().showNextCandidatePairs(creatorThread.getNextCandidatePairAmount());
                mainWindow.getMainWriterPanel().getKbQueuePanel().showConsistentQueue(kbWriter.getConsistentQueue());
                mainWindow.getMainWriterPanel().getKbQueuePanel().showInconsistentQueue(kbWriter.getInconsistentQueue());
                mainWindow.getMainWriterPanel().getMainKbWriterPanel().showSpeed(kbWriter.getConsistentCounter() + kbWriter.getInconsistentCounter());
                mainWindow.getMainWriterPanel().getMainKbWriterPanel().showConsistentConter(kbWriter.getConsistentCounter());
                mainWindow.getMainWriterPanel().getMainKbWriterPanel().showIncosnsistentCounter(kbWriter.getInconsistentCounter());


                //cp writer thread is started after this thread, so this will avoid the null pointer exception
                if (creatorThread.getPairBuffer() != null) {
                    mainWindow.getMainWriterPanel().getCandidatesPanel().showWriterQueue(creatorThread.getPairBuffer().getQueueToWriteSize());
                    mainWindow.getMainWriterPanel().getCandidatesPanel().showReaderProgress(creatorThread.getPairBuffer().getReaderCounter());
                    mainWindow.getMainWriterPanel().getCandidatesPanel().showStatus(creatorThread.getPairBuffer().getStatus());
                }


                //todo: think about this. this should be there for consistent and inconsistent?!
                if ((kbWriter.getConsistentQueue() + kbWriter.getInconsistentQueue()) > 100_000)

                    creatorThread.waitForKbWriter();

                if ((kbWriter.getConsistentQueue() + kbWriter.getInconsistentQueue()) < 5000)
                    synchronized (creatorThread) {
                        creatorThread.notify();
                    }
/*
                if (kbWriter.getInconsistentQueue() + kbWriter.getConsistentQueue() < 200)
                    kbWriter.notify();
*/

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
        this.kbWriter = creatorThread.getKbWriterThread();
    }


}
