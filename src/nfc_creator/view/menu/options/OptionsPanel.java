package nfc_creator.view.menu.options;

import kb_creator.model.logic.signature.AbstractSignature;
import nfc_creator.model.WConditional;
import nfc_creator.model.WorldsList;
import nfc_creator.view.textArea.CondTextField;

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


    public void applySelectedOptions() {
        WorldsList.setView(viewPanel.getRequestedView());
        WorldsList.setSignature(signaturePanel.getRequestedSignature());
        WConditional.setSpaceDot(dotsPanel.isDotsViewActive());
        CondTextField.setNumberingActive(numbersPanel.isNumbersActive());

    }

    public AbstractSignature getSignature() {
        return signaturePanel.getRequestedSignature();
    }

}
