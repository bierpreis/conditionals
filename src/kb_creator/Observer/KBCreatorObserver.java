package kb_creator.Observer;

import kb_creator.gui.KBMainWindow;
import kb_creator.model.KBCreator;
import nfc.model.NfcCreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KBCreatorObserver implements ActionListener {
    private KBMainWindow mainWindow;
    private StatusThread statusThreadObject;


    public KBCreatorObserver() {

        mainWindow = new KBMainWindow(this);


    }

    @Override

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Start") {

            NfcCreator nfcCreator = new NfcCreator(mainWindow.getSignature());
            KBCreator creatorRunnable = new KBCreator(this, nfcCreator.getNfc(), nfcCreator.getCnfc());


            Thread creatorThread = new Thread(creatorRunnable);
            creatorThread.start();

            Thread statusThread = new Thread(statusThreadObject = new StatusThread(mainWindow.getInfoPanel(), creatorRunnable));
            statusThread.start();

        }

        if (e.getActionCommand().equals("Stop")) {

            try {
                //todo: stop creator thread too!
                statusThreadObject.halt();
            } catch (Exception exep) {
                System.out.println("tried to stop thread, but no thread running");

            }

        }
    }
}
