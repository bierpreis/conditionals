package kb_creator.observer;

import kb_creator.gui.MainWindow;
import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.buffer.BufferingType;
import kb_creator.model.buffer.hdd.HddPairBuffer;
import kb_creator.model.buffer.ram.CompressedRamBuffer;
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

                candidateBuffer = getCandidateBuffer(mainWindow);

                mainWindow.getLeftPanel().getMainOptionsPanel().setActive(false);

                creatorThreadObject = new Creator(mainWindow.getSignature(), mainWindow.getKbFilePath(), candidateBuffer, mainWindow.getKbNameLength(), mainWindow.getKbNumbmber());

                creatorThread = new Thread(creatorThreadObject);
                creatorThread.setName("MainCreatorThread");
                creatorThread.start();

                creatorThread.setPriority(Thread.MAX_PRIORITY);

                statusThreadObject.setCreatorThread(creatorThreadObject);

                creatorThreadObject.getPairBuffer().setDeletingFiles(mainWindow.getLeftPanel().getMainOptionsPanel().isDeletingBufferFilesRequested());

            }
        }

        //todo: after stop and not choosing writer writer is active again! writer is closed but started again!
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

    private AbstractPairBuffer getCandidateBuffer(MainWindow mainWindow) {
        AbstractPairBuffer buffer;
        if (mainWindow.getBufferingType().equals(BufferingType.HDD))
            buffer = new HddPairBuffer(mainWindow.getCpFilePath(), mainWindow.getLeftPanel().getMainOptionsPanel().getBufferPanel().getBufferSize());
        else if (mainWindow.getBufferingType().equals(BufferingType.COMPRESSED_RAM))
            buffer = new CompressedRamBuffer();
        else buffer = new SimpleRamBuffer();
        return buffer;
    }


}
