package kb_creator.observer;

import kb_creator.gui.MainWindow;
import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.buffer.hdd.HddPairBuffer;
import kb_creator.model.buffer.ram.RamPairBuffer;
import kb_creator.model.buffer.simple_ram.SimpleRamBuffer;
import kb_creator.model.creator.Creator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatorButtonObserver implements ActionListener {
    private MainWindow mainWindow;
    private AbstractPairBuffer candidateBuffer;

    private Creator creatorThreadObject;
    private GuiStatusThread statusThreadObject;

    private Thread creatorThread;


    public CreatorButtonObserver() {

        mainWindow = new MainWindow(this);
        statusThreadObject = new GuiStatusThread(mainWindow);

        Thread statusThread = new Thread(statusThreadObject);

        statusThread.start();
        statusThread.setName("StatusThread");

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (mainWindow.getLeftPanel().getMainOptionsPanel().areValuesValid()) {
            if (e.getActionCommand().equals("Start")) {
                mainWindow.getRightPanel().setActive(true);


                //todo: implement all 3 options
                if (mainWindow.isBufferingRequested())
                    candidateBuffer = new HddPairBuffer(mainWindow.getCpFilePath(), mainWindow.getLeftPanel().getMainOptionsPanel().getBufferPanel().getBufferSize(), mainWindow.getLeftPanel().getMainOptionsPanel().getBufferPanel().getFileNameLengthPanel().getNumberOfDigits());
                else candidateBuffer = new SimpleRamBuffer();


                mainWindow.getLeftPanel().getMainOptionsPanel().setActive(false);

                creatorThreadObject = new Creator(mainWindow.getSignature(), mainWindow.getKbFilePath(), candidateBuffer);

                creatorThread = new Thread(creatorThreadObject);
                creatorThread.setName("MainCreatorThread");
                creatorThread.start();

                creatorThread.setPriority(Thread.MAX_PRIORITY);

                statusThreadObject.setCreatorThread(creatorThreadObject);

                creatorThreadObject.getPairBuffer().setDeletingFiles(mainWindow.getLeftPanel().getMainOptionsPanel().isDeletingBufferFilesRequested());

            }
        }

        if (e.getActionCommand().equals("Stop")) {
            UIManager.put("OptionPane.yesButtonText", "Yes, Stop.");
            UIManager.put("OptionPane.noButtonText", "No, Continue.");

            int answer = JOptionPane.showConfirmDialog(mainWindow.getMainWindow(), "Do you really want to Stop?", "Warning", JOptionPane.YES_NO_OPTION);

            if (answer == 0) {
                mainWindow.getRightPanel().setActive(false);
                mainWindow.getLeftPanel().getMainOptionsPanel().setActive(true);
                mainWindow.getLeftPanel().getMainOptionsPanel().init();

                creatorThreadObject.stopLoop();
                candidateBuffer.stopLoop();

                creatorThread.interrupt();
            }

        }
    }


}
