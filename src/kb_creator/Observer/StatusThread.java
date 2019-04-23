package kb_creator.Observer;

import kb_creator.gui.leftpanel.StatusPanel;

public class StatusThread implements Runnable {
    private StatusPanel statusPanel;
    private Thread statusThread;

    public StatusThread(StatusPanel statusPanel) {
        this.statusPanel = statusPanel;


    }



    @Override
    public void run() {
        while (true) {//todo this
            statusPanel.showInfo();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
