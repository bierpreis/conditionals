package kb_creator.gui.left_panel.optionsPanel.kb_save_options_panel;

import javax.swing.*;

public class InputWarningDialog extends JDialog {
    JLabel label = new JLabel(" ");
    public InputWarningDialog(){
        label.setText("Warning! Invalid input for file name length!");
    }
}
