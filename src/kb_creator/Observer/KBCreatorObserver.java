package kb_creator.Observer;

import kb_creator.gui.KBMainWindow;
import kb_creator.model.KBCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KBCreatorObserver implements ActionListener {
    private KBMainWindow mainWindow;

    private KBCreator creatorThreadObject;


    public KBCreatorObserver() {

        mainWindow = new KBMainWindow(this);

        creatorThreadObject = new KBCreator();

        StatusThread statusThreadObject;
        statusThreadObject = new StatusThread(mainWindow.getInfoPanel(), creatorThreadObject);

        Thread statusThread = new Thread(statusThreadObject);

        statusThread.start();
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {


            creatorThreadObject.setSignature(mainWindow.getSignature());

            Thread creatorThread = new Thread(creatorThreadObject);


            creatorThread.start();

        }

        if (e.getActionCommand().equals("Stop")) {
            UIManager.put("OptionPane.yesButtonText", "Yes, Stop.");
            UIManager.put("OptionPane.noButtonText", "No, Continue.");

            int answer = JOptionPane.showConfirmDialog(mainWindow.getMainWindow(), "Do you really want to Stop?", "Warning", JOptionPane.YES_NO_OPTION);

            if (answer == 1)
                return;
            if (answer == 0)
                try {//todo: is this try catch still neede?
                    creatorThreadObject.stop();
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
