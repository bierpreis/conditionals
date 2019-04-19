package nfc.view.menu;

import nfc.view.textArea.ViewOptions;

import javax.swing.*;
import java.util.HashMap;


public class OptionsPanel extends JPanel {


    private SignaturePanel signaturePanel;
    private NumbersPanel numbersPanel;
    private ViewPanel viewPanel;
    private SpacePanel spacePanel;


    public OptionsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        numbersPanel = new NumbersPanel();
        setBorder(BorderFactory.createTitledBorder("Options"));
        add(signaturePanel = new SignaturePanel());
        add(viewPanel = new ViewPanel());
        add(numbersPanel);

        add(spacePanel = new SpacePanel());
    }

    //todo: change this
    public ViewOptions getOptions() {
        ViewOptions options = new ViewOptions();

        options.setShowNumbers(numbersPanel.getOption());

        //options.put("signature", signaturePanel.getOption());
        //options.put("numbering", numbersPanel.getOption());
        //options.put("nfc/view", viewPanel.getOption());
        //options.put("space", spacePanel.getOption());
        return options;
    }

    public String getSignature(){
        return signaturePanel.getOption();
    }

}
