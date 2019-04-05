package view.menu;

import controller.GuiObserver;

import javax.swing.*;


public class OptionsPanel extends JPanel {


    private SignaturePanel signaturePanel;
    private NumbersPanel numbersPanel;


    public OptionsPanel(GuiObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        numbersPanel = new NumbersPanel(observer);
        setBorder(BorderFactory.createTitledBorder("Options"));
        add(signaturePanel = new SignaturePanel(observer));
        add(new ViewPanel(observer));
        add(numbersPanel);


        setVisible(true);
    }

}
