package view;

import controller.GuiObserver;
import view.menu.MenuPanel;
import view.textArea.CondPanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    JFrame mainWindow;
    CondPanel condPanel;

    public MainWindow(GuiObserver observer) {
        mainWindow = new JFrame();
        mainWindow.setTitle("NFC Creator");

        mainWindow.setLayout(new BoxLayout(mainWindow.getContentPane(), BoxLayout.X_AXIS));

        condPanel = new CondPanel();


        mainWindow.add(new MenuPanel(observer));
        mainWindow.getContentPane().add(condPanel);


        mainWindow.setMinimumSize(new Dimension(300, 500));

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    public CondPanel getCondPanel() {
        return condPanel;
    }
}
