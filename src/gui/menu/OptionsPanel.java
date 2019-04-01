package gui.menu;

import javax.swing.*;


public class OptionsPanel extends JPanel {


    private SignaturePanel signaturePanel;


    public OptionsPanel() {
        setBorder(BorderFactory.createTitledBorder("Options"));
        add(signaturePanel = new SignaturePanel());
        add(new ViewPanel());


        setVisible(true);
    }

    public String getSignature() { //todo: delete maybe? listener wie optionspanel

        return signaturePanel.getSignatureButtonGroup().getSelection().getActionCommand();

    }


}
