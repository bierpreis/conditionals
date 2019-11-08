package nfc.view.menu.options;

import kb_creator.model.propositional_logic.signature.AbstractSignature;
import nfc.model.WConditional;
import nfc.model.WorldList;
import nfc.view.textArea.CondTextField;

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
        WorldList.setView(viewPanel.getRequestedView());
        WorldList.setSignature(signaturePanel.getRequestedSignature());
        WConditional.setSpaceDot(dotsPanel.isDotsViewActive());
        CondTextField.setNumberingActive(numbersPanel.isNumbersActive());

    }

    public AbstractSignature getSignature() {
        return signaturePanel.getRequestedSignature();
    }

}
