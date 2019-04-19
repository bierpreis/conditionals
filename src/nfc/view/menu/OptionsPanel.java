package nfc.view.menu;

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

    //todo: change this
    public ViewOptions getOptions() {
        ViewOptions options = new ViewOptions();

        options.setShowNumbers(numbersPanel.getOption());
        options.setShowDots(dotsPanel.getOption());
        options.setTwoLetters(signaturePanel.getOption());

        options.setShowLetters(viewPanel.isLettersViewActive());

        return options;
    }

    //todo: rework
    public String getSignature() {
        if (signaturePanel.getOption())
            return "ab";
        else return "abc";
    }

}
