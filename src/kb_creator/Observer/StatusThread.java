package kb_creator.Observer;

import kb_creator.gui.leftpanel.StatusPanel;
import kb_creator.model.KBCreator;

public class StatusThread implements Runnable {
    private StatusPanel statusPanel;
    private boolean running = true;
    private KBCreator creatorThread;

    public StatusThread(StatusPanel statusPanel, KBCreator creatorThread) {
        this.statusPanel = statusPanel;
        this.creatorThread = creatorThread;

    }


    @Override
    public void run() {
        while (running) {
            statusPanel.showInfo(Integer.toString(creatorThread.getCounter()));

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void halt() {
        running = false;
    }
}
