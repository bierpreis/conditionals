package view;

import controller.GuiObserver;
import view.menu.MenuPanel;
import view.textArea.CondPanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    JFrame mainWindow;

    public MainWindow(GuiObserver observer) {
        mainWindow = new JFrame();
        mainWindow.setTitle("NFC Creator");

        mainWindow.setLayout(new BoxLayout(mainWindow.getContentPane(), BoxLayout.X_AXIS));

        CondPanel condPanel = new CondPanel();


        mainWindow.add(new MenuPanel(condPanel, observer));
        mainWindow.getContentPane().add(condPanel);


        mainWindow.setMinimumSize(new Dimension(300, 500));

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.pack();
        mainWindow.setVisible(true);
    }
}
