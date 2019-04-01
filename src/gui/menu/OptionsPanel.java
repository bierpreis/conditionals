package gui.menu;

import javax.swing.*;


public class OptionsPanel extends JPanel {


    private JPanel signaturePanel;


    public OptionsPanel() {
        setBorder(BorderFactory.createTitledBorder("Options"));
        add(signaturePanel = new SignaturePanel());
        add(new ViewPanel());


        setVisible(true);
    }

    public String getSignature() { //todo: delete maybe? FIX THIS!!

        //return signaturePanel.getSignatureButtonGroup().getSelection().getActionCommand();
        return "ab";
    }


}
