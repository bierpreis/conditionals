package nfc.view.menu;

import nfc.model.World;
import nfc.view.textArea.ViewOptions;

import javax.swing.*;


public class OptionsPanel extends JPanel {


    private SignaturePanel signaturePanel;
    private NumbersPanel numbersPanel;
    private ViewPanel viewPanel;
    private DotsPanel dotsPanel;


    public OptionsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        numbersPanel = new NumbersPanel();
        setBorder(BorderFactory.createTitledBorder("Options"));
        add(signaturePanel = new SignaturePanel());
        add(viewPanel = new ViewPanel());
        add(numbersPanel);

        add(dotsPanel = new DotsPanel());
    }

    //todo: unify
    public ViewOptions getOptions() {
        ViewOptions options = new ViewOptions();

        World.setView(viewPanel.getRequestedView());
        World.setSignature(signaturePanel.getRequestedSignature());

        options.setShowNumbers(numbersPanel.isNumbersActive());
        options.setShowDots(dotsPanel.isDotsViewActive());

        return options;
    }

    public String getSignature() {
        return signaturePanel.getRequestedSignature();
    }

}
