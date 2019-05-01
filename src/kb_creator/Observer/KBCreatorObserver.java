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

        creatorThreadObject = new KBCreator();//todo: always create new cb creator on start? would be better. e.g. number
        //of knowledge bases increases after creating multiple times ->fix this

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

            creatorThreadObject.pause(true);
        if (e.getActionCommand().equals("Continue"))

            creatorThreadObject.pause(false);


    }
}
