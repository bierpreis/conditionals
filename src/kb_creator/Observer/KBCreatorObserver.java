package kb_creator.Observer;

import kb_creator.gui.KBMainWindow;
import kb_creator.model.Conditionals.KBCreator;
import kb_creator.model.Conditionals.Lists.SimpleBufferedList;
import kb_creator.model.Conditionals.Lists.UnbufferedList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KBCreatorObserver implements ActionListener {
    private KBMainWindow mainWindow;

    private KBCreator creatorThreadObject;
    private StatusThread statusThreadObject;


    public KBCreatorObserver() {

        mainWindow = new KBMainWindow(this);
        statusThreadObject = new StatusThread(mainWindow);

        Thread statusThread = new Thread(statusThreadObject);

        statusThread.start();

    }

    @Override

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {

            creatorThreadObject = new KBCreator(mainWindow.getSignature(), mainWindow.getKbFilePath());

            if (mainWindow.isBufferingRequested())
                creatorThreadObject.setList(new SimpleBufferedList(mainWindow.getCpFilePath()));
            else creatorThreadObject.setList(new UnbufferedList());


            Thread creatorThread = new Thread(creatorThreadObject);


            creatorThread.start();

            statusThreadObject.setCreatorThread(creatorThreadObject);

        }

        if (e.getActionCommand().equals("Stop")) {
            creatorThreadObject.pause(true);
            UIManager.put("OptionPane.yesButtonText", "Yes, Stop.");
            UIManager.put("OptionPane.noButtonText", "No, Continue.");

            int answer = JOptionPane.showConfirmDialog(mainWindow.getMainWindow(), "Do you really want to Stop?", "Warning", JOptionPane.YES_NO_OPTION);

            if (answer == 1) {
                creatorThreadObject.pause(false);
                return;
            }
            if (answer == 0)
                creatorThreadObject.stop();

        }
        if (e.getActionCommand().equals("Pause"))

            creatorThreadObject.pause(true);
        if (e.getActionCommand().equals("Continue"))

            creatorThreadObject.pause(false);


    }
}
