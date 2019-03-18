package gui;


import groovy.swing.factory.LayoutFactory;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new WorldsPanel());
        add(new StartPanel());
    }
}
