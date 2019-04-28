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

        if (viewPanel.isLettersViewActive())
            World.setView("letters");
        else World.setView("numbers");

        options.setShowNumbers(numbersPanel.isNumbersActive());
        options.setShowDots(dotsPanel.isDotsViewActive());
        options.setTwoLetters(signaturePanel.isTwoLettersActive());
        options.setLettersView(viewPanel.isLettersViewActive());

        return options;
    }

    public String getSignature() {
        if (signaturePanel.isTwoLettersActive())
            return "ab";
        else return "abc";
    }

}
