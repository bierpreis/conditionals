package kb_creator.Observer;

import kb_creator.gui.KBMainWindow;
import kb_creator.model.KBCreator;
import nfc.model.NfcCreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KBCreatorObserver implements ActionListener {
    private KBMainWindow mainWindow;

    public KBCreatorObserver() {

        mainWindow = new KBMainWindow(this);


        Thread statusThread = new Thread(new StatusThread(mainWindow.getStatusPanel()));
        statusThread.start();
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        NfcCreator nfcCreator = new NfcCreator(mainWindow.getSignature());
        Runnable creator = new KBCreator(this, nfcCreator.getNfc(), nfcCreator.getCnfc());
        Thread creatorThread;
        if (e.getActionCommand() == "Start") {

            creatorThread = new Thread(creator);
            creatorThread.start();


        }
    }
}
