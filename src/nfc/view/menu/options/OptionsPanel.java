package nfc.view.menu.options;

import nfc.model.Conditional;
import nfc.model.World;
import nfc.view.textArea.CondTextField;
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

    //todo: unify and make void
    public ViewOptions applySelectedOptions() {
        ViewOptions options = new ViewOptions();

        World.setView(viewPanel.getRequestedView());
        World.setSignature(signaturePanel.getRequestedSignature());
        Conditional.setSpaceDot(dotsPanel.isDotsViewActive());
        CondTextField.setNumberingActive(numbersPanel.isNumbersActive());
        
        return options;
    }

    public String getSignature() {
        return signaturePanel.getRequestedSignature();
    }

}
