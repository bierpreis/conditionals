package gui;


import javax.swing.*;

public class CondPanel extends JPanel {
    CondTextField condTextField;

    public CondPanel() {
        condTextField = new CondTextField();
        add(condTextField);
    }

    public void printConditionals(){
        condTextField.printConditionals();
    }
}
