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

    @Override
    public void run() {
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            if (creatorThread != null) {
                mainWindow.getCreatorPanel().showStatus(creatorThread.getStatus());
                mainWindow.getCreatorPanel().showIterationKBs(creatorThread.getIterationNumberOfKBs());
                mainWindow.getCreatorPanel().showKBAmount(creatorThread.getTotalKbAmount());

                mainWindow.getCreatorPanel().showCurrentK(creatorThread.getCurrentK());

                mainWindow.getCreatorPanel().showSpeed(calcSpeed(creatorThread.getTotalKbAmount()));

                //removed because information didnt work with parallel buffer //todo: here show the "last" cp number. but where to save it?
                // mainWindow.getCreatorPanel().showCurrentCandidatePairs(creatorThread.getCurrentK());

                mainWindow.getCreatorPanel().showNextCandidatePairs(creatorThread.getNextCandidatePairAmount());
                mainWindow.getKbWriterPanel().getKbQueuePanel().showConsistentQueue(kbWriter.getConsistentQueue());
                mainWindow.getKbWriterPanel().getKbQueuePanel().showInconsistentQueue(kbWriter.getInconsistentQueue());
                mainWindow.getKbWriterPanel().getMainKbWriterPanel().showSpeed(kbWriter.getConsistentCounter() + kbWriter.getInconsistentCounter());
                mainWindow.getKbWriterPanel().getMainKbWriterPanel().showConsistentConter(kbWriter.getConsistentCounter());
                mainWindow.getKbWriterPanel().getMainKbWriterPanel().showIncosnsistentCounter(kbWriter.getInconsistentCounter());


                //cp writer thread is started after this thread, so this will avoid the null pointer exception
                if (creatorThread.getCpWriterThread() != null) {
                    mainWindow.getKbWriterPanel().getCandidatesPanel().showWriterQueue(creatorThread.getCpWriterThread().getQueueToWriteSize());
                    mainWindow.getKbWriterPanel().getCandidatesPanel().showReaderProgress(creatorThread.getCpWriterThread().getReaderCounter());
                    mainWindow.getKbWriterPanel().getCandidatesPanel().showStatus(creatorThread.getCpWriterThread().getStatus());
                }

                if ((kbWriter.getConsistentQueue() + kbWriter.getInconsistentQueue()) > 100_000)

                    creatorThread.setWaiting();

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
