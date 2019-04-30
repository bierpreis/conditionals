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

            //todo: fix null pointer when status thread running and creator not started yet
            creatorThreadObject = new KBCreator(this);
            Thread creatorThread = new Thread(creatorThreadObject);

            Thread statusThread = new Thread(statusThreadObject = new StatusThread(mainWindow.getInfoPanel(), creatorThreadObject));
            statusThread.start();
            System.out.println("status Thread started");
            statusThreadObject.setStatus("Creating Conditionals");
            System.out.println("crating ..");
            NfcCreator nfcCreator = new NfcCreator(mainWindow.getSignature());
            creatorThreadObject.setConditionals(nfcCreator.getNfc(), nfcCreator.getCnfc());

            statusThreadObject.setStatus("Creating Knowledge Bases");
            creatorThread.start();


        }

        if (e.getActionCommand().equals("Stop")) {


            try {
                creatorThreadObject.stop();
                //statusThreadObject.halt();
            } catch (Exception exep) {
                System.out.println("Click on Stop Button Ignored cause no Thread running.");

            }

        }
        if (e.getActionCommand().equals("Pause"))
            try {
                creatorThreadObject.pause(true);
            } catch (NullPointerException np) {
                System.out.println("Click on Pause Button Ignored cause no creator Thread is running.");
            }
        if (e.getActionCommand().equals("Continue"))
            try {
                creatorThreadObject.pause(false);
            } catch (NullPointerException np) {
                System.out.println("Click on Pause Button Ignored cause no creator Thread is running.");
            }
    }
}
