package view.menu;

import controller.GuiObserver;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class OptionsPanel extends JPanel {


    private SignaturePanel signaturePanel;
    private NumbersPanel numbersPanel;
    private ViewPanel viewPanel;


    public OptionsPanel(GuiObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        numbersPanel = new NumbersPanel(observer);
        setBorder(BorderFactory.createTitledBorder("Options"));
        add(signaturePanel = new SignaturePanel(observer));
        add(viewPanel = new ViewPanel(observer));
        add(numbersPanel);


        setVisible(true);
    }

    public HashMap<String, String> getOptions() {
        HashMap<String, String> options = new HashMap<>();
        options.put("signature", signaturePanel.getOption());
        options.put("numbering", numbersPanel.getOption());
        options.put("view", viewPanel.getOption());
        return options;
    }

}
