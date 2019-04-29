package kb_creator.Observer;

import kb_creator.gui.KBMainWindow;
import kb_creator.model.KBCreator;
import nfc.model.NfcCreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KBCreatorObserver implements ActionListener {
    private KBMainWindow mainWindow;
    private StatusThread statusThreadObject;
    private KBCreator creatorThreadObject;


    public KBCreatorObserver() {

        mainWindow = new KBMainWindow(this);


    }

    @Override

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Start") {

            NfcCreator nfcCreator = new NfcCreator(mainWindow.getSignature());
            creatorThreadObject = new KBCreator(this, nfcCreator.getNfc(), nfcCreator.getCnfc());


            Thread creatorThread = new Thread(creatorThreadObject);
            creatorThread.start();

            Thread statusThread = new Thread(statusThreadObject = new StatusThread(mainWindow.getInfoPanel(), creatorThreadObject));
            statusThread.start();

        }

        if (e.getActionCommand().equals("Stop")) {

            try {
                //todo: stopped works, but doenst kill thread so it starts flickering when new thread starts later. maybe use this for pause function?
                creatorThreadObject.stop();
                statusThreadObject.halt();
            } catch (Exception exep) {
                exep.printStackTrace();

            }

        }

        if (e.getActionCommand().equals("Pause"))
            creatorThreadObject.pause(); //todo: null pointer if this is clicked and no thread exists. probably the same with above
    }
}
