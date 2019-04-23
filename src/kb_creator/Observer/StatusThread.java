package kb_creator.Observer;

import kb_creator.gui.leftpanel.StatusPanel;

public class StatusThread implements Runnable {
    private StatusPanel statusPanel;
    private boolean running = true;

    public StatusThread(StatusPanel statusPanel) {
        this.statusPanel = statusPanel;


    }


    @Override
    public void run() {
        while (running) {
            statusPanel.showInfo();

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
