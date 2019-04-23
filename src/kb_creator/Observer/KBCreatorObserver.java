package kb_creator.Observer;

import kb_creator.gui.KBMainWindow;
import kb_creator.model.KBCreatorThread;
import nfc.model.NfcCreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KBCreatorObserver implements ActionListener {
    private KBMainWindow mainWindow;
    private Thread creatorThread;
    private StatusThread statusThreadObject;


    public KBCreatorObserver() {

        mainWindow = new KBMainWindow(this);


    }
    //todo: connect thread and statuspanel

    @Override

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Start") {

            NfcCreator nfcCreator = new NfcCreator(mainWindow.getSignature());
            Runnable creatorThread = new KBCreatorThread(this, nfcCreator.getNfc(), nfcCreator.getCnfc());


            this.creatorThread = new Thread(creatorThread);
            this.creatorThread.start();

            Thread statusThread = new Thread(statusThreadObject = new StatusThread(mainWindow.getStatusPanel()));
            statusThread.start();

        }

        if (e.getActionCommand().equals("Stop")) {

            try {
                statusThreadObject.halt();
            } catch (Exception exep) {
                System.out.println("tried to stop thread, but no thread running");

            }

        }
    }
}
