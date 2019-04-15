package view.menu;

import controller.GuiObserver;
import model.Command;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {


    public StartPanel(GuiObserver observer) {
        setLayout(new GridLayout());
        setBorder(BorderFactory.createTitledBorder("start"));

        AbstractNfcButton createNfcButton = new AbstractNfcButton(Command.NFC);

        AbstractNfcButton createCnfcButton = new AbstractNfcButton(Command.CNFC);
        AbstractNfcButton createCnfcEqButton = new AbstractNfcButton(Command.CNFCEQ);


        createNfcButton.addActionListener(observer);
        add(createNfcButton);

        createCnfcButton.addActionListener(observer);
        add(createCnfcButton);

        createCnfcEqButton.addActionListener(observer);
        add(createCnfcEqButton);

    }


}

