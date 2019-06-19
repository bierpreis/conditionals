package nfc.view.menu.options;

import kb_creator.model.propositional_logic.Signature.AbstractSignature;
import nfc.model.Conditional;
import nfc.model.World;
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
        World.setView(viewPanel.getRequestedView());
        World.setSignature(signaturePanel.getRequestedSignature());
        Conditional.setSpaceDot(dotsPanel.isDotsViewActive());
        CondTextField.setNumberingActive(numbersPanel.isNumbersActive());

    }

    public AbstractSignature getSignature() {
        return signaturePanel.getRequestedSignature();
    }

}
