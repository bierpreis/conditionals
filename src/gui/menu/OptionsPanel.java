package gui.menu;

import javax.swing.*;


public class OptionsPanel extends JPanel {


    private SignaturePanel signaturePanel;
    private NumbersPanel numbersPanel;


    public OptionsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        numbersPanel = new NumbersPanel();
        setBorder(BorderFactory.createTitledBorder("Options"));
        add(signaturePanel = new SignaturePanel());
        add(new ViewPanel());
        add(numbersPanel);


        setVisible(true);
    }

    public String getSignature() { //todo: delete maybe? listener wie optionspanel

        return signaturePanel.getSignatureButtonGroup().getSelection().getActionCommand();

    }


}
