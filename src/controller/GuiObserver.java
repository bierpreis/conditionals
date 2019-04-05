package controller;

import view.MainWindow;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GuiObserver implements PropertyChangeListener {

    public GuiObserver() {
        MainWindow mainWindow = new MainWindow(this);
    }

    public void propertyChange(PropertyChangeEvent e) {
        System.out.println("property changed: ");
        System.out.println(e.getPropertyName() + e.getNewValue());
    }
}
