package kb_creator.Observer;

import kb_creator.gui.KBMainWindow;
import kb_creator.model.KBCreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KBCreatorObserver implements ActionListener {
    private KBMainWindow mainWindow;

    private StatusThread statusThreadObject;
    private KBCreator creatorThreadObject;

    Thread statusThread;


    public KBCreatorObserver() {

        mainWindow = new KBMainWindow(this);

        creatorThreadObject = new KBCreator(this);

        statusThreadObject = new StatusThread(mainWindow.getInfoPanel(), creatorThreadObject);

        statusThread = new Thread(statusThreadObject);

        statusThread.start();
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        System.out.println("command: " + e.getActionCommand());
        if (e.getActionCommand().equals("Start")) {


            creatorThreadObject.setSignature(mainWindow.getSignature());

            Thread creatorThread = new Thread(creatorThreadObject);


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
